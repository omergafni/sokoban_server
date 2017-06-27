package com.server.model.client;

import com.server.model.planner.plannable.PlanAction;
import com.server.model.planner.planner.Strips;
import com.server.model.searcher.searchable.Position;
import com.server.model.searcher.searcher.BFS;
import com.server.model.sokoban.SokobanHeuristic;
import com.server.model.sokoban.SokobanPlannable;
import com.server.model.sokoban.SokobanSolver;
import common.LevelGrid;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SokobanClientHandler implements ClientHandler {

    private final String url = "http://localhost:8080/webresources/solutions";
    PrintWriter out = null;
    LevelGrid level = null;


    public void handleClient(InputStream fromClient, OutputStream toClient) {
        ObjectInputStream ois = null;
        PrintWriter oos = null;
        out = new PrintWriter(toClient);
        try {
            ois = new ObjectInputStream(fromClient);
            oos = new PrintWriter(toClient);
            level = (LevelGrid) ois.readObject();

            // Access to web service and check if there is a solution
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(url + "?levelName=" + level.getLevelName());
            Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                String solution = response.readEntity(new GenericType<String>() {
                });
                // Solve if necessary
                if (solution.equals("")) {
                    solution = solveLevel(level.getGrid());
                    updateWebService(level.getLevelName(),solution);
                }
                // Solution is here, then send it to the client
                out.println(solution);
                out.flush();
            } else {
                System.out.println(response.getHeaderString("errorResponse"));
                PrintWriter out = new PrintWriter(toClient);
                out.println("error");
                out.flush();
            }
        }
        catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
        finally {
            try {
                if (ois != null) ois.close();
                if (oos != null) oos.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Update the web service with the new solution for the given level
     * @param levelName is the Id
     * @param solution the compresses solution
     */
    private void updateWebService(String levelName, String solution) {

        // Access to web service and check if there is a solution
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url + "/add?levelName=" + levelName + "&solution="+solution);
        Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);
        if(response.getStatus() != 200 && response.getStatus() != 204){
            System.out.println("error updated web service");
        }

    }


    /**
     * Solves the level using the solving algorithm and return the parsed solution
     * @param grid represent the level grid data
     * @return is the compressed solution format
     */
    private String solveLevel(ArrayList<char[]> grid) {

        SokobanPlannable sp = new SokobanPlannable(grid, new BFS<Position>(), new BFS<Position>());
        SokobanSolver solver = new SokobanSolver(sp, new Strips());
        solver.setHeuristic(new SokobanHeuristic()); // Setting heuristic method to the solver
        List<PlanAction> plan = solver.solve(); // Getting the solution

        StringBuilder solution = new StringBuilder();

        // Parsing the actions to the desired compressed format
        for(PlanAction a : plan){
            char c = a.toString().split(" ")[1].charAt(0);
            solution.append(c);
        }
        return solution.toString();
    }

}
