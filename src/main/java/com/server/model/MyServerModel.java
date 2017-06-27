package com.server.model;

import com.server.model.client.ClientHandler;
import com.server.model.client.ConnectedClient;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyServerModel extends Observable implements ServerModel {

    private int port;
    private ClientHandler ch;
    private boolean runServer;
    private ExecutorService threadPool;
    private static final int THREADS_NUM = 30;
    private boolean autoSolve = true;

    public MyServerModel(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        runServer = true;
        threadPool = Executors.newFixedThreadPool(THREADS_NUM);
    }

    @Override
    public void runServer() throws IOException {
        System.out.println("server is online");
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(5000);
        while (runServer) {
            try {
                final Socket aClient = server.accept();
                System.out.println("client accepted");
                threadPool.execute(()->{
                    {
                        try {
                            // Check auto-solve condition

                            // if false -> wait for approve..

                            // if true:
                            ConnectedClient client = new ConnectedClient(new SimpleStringProperty("Leon"),
                                    new SimpleStringProperty("level"),new SimpleStringProperty("12:00"),
                                    new SimpleStringProperty("connected"));
                            setChanged();
                            notifyObservers(client);
                            /*
                            ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                            aClient.getInputStream().close();
                            aClient.getOutputStream().close();
                            aClient.close();
                            */
                            if(false)
                                throw new IOException();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (SocketTimeoutException e) {
                System.out.println("time out occurred");
            }
        }
        server.close();
        System.out.println("server closed");
    }

    public void stop() {
        threadPool.shutdown();
        try{
            threadPool.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally {
            runServer = false;
        }
    }

    public ClientHandler getClientHandler() {
        return this.ch;
    }

    @Override
    public void setAutoSolve(boolean bool) {
        this.autoSolve = bool;
    }
}
