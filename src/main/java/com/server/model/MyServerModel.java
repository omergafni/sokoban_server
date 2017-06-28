package com.server.model;

import com.server.model.client.ClientHandler;
import com.server.model.client.ClientThread;
import com.server.model.client.ConnectedClient;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyServerModel extends Observable implements ServerModel {

    private static int threadId = 0;
    private static final int THREADS_NUM = 30;
    private int port;
    private ClientHandler ch;
    private boolean runServer;
    private ExecutorService threadPool;
    private boolean autoSolve = true;
    private Map<Integer, ClientThread> threadMap;

    public MyServerModel(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        this.runServer = true;
        this.threadPool = Executors.newFixedThreadPool(THREADS_NUM);
        this.threadMap = new HashMap<>();
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
                // Extract client information and update the ViewModel (uses for the admin control panel)
                createClientData(aClient, threadId);
                ClientThread clientThread = new ClientThread(this,aClient);
                if(!autoSolve)
                    threadMap.put(threadId, clientThread);
                threadPool.execute(clientThread);
                threadId++;
            } catch (SocketTimeoutException e) {
                System.out.println("time out occurred");
            }
        }
        server.close();
        System.out.println("server closed");
    }

    @Override
    public void awakeClient(int clientThreadID) {
        System.out.println("client " + clientThreadID + " is awake");
        ClientThread t = threadMap.get(clientThreadID);
        threadMap.remove(clientThreadID);
        synchronized (t){
            t.notify();
        }
    }

    @Override
    public void disconnectClient(int clientThreadID){
        ClientThread t = threadMap.get(clientThreadID);
        threadMap.remove(clientThreadID);
        t.setDisconnect(true);
        synchronized (t){
            t.notify();
        }
    }

    public void handleConnectedClient(Socket aClient) {
        try {
            // Communicate with client and send the solution
            ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
            aClient.getInputStream().close();
            aClient.getOutputStream().close();
            aClient.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createClientData(Socket aClient, int threadId) {

        // Create the user connection time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleStringProperty time = new SimpleStringProperty(sdf.format(Calendar.getInstance().getTime()));
        // Create other data
        SimpleStringProperty clientId = new SimpleStringProperty(aClient.getRemoteSocketAddress().toString().replace("/", ""));
        SimpleStringProperty status;
        if (autoSolve) {
            status = new SimpleStringProperty("connected");
        } else {
            status = new SimpleStringProperty("Awaiting Approval");
        }
        ConnectedClient client = new ConnectedClient(clientId, time, status, threadId);
        setChanged();
        notifyObservers(client);
    }

    public void stop() {
        threadPool.shutdown();
        threadMap.forEach((integer, clientThread) -> {
            clientThread.setDisconnect(true);
            synchronized (clientThread){
                clientThread.notify();
            }
        });

        try {
            threadPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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

    public boolean isAutoSolve() {
        return autoSolve;
    }
}
