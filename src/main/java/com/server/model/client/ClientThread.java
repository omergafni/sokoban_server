package com.server.model.client;

import com.server.model.MyServerModel;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private MyServerModel theServer;
    private Socket aClient;
    private boolean isDisconnect;

    public ClientThread(MyServerModel theServer, Socket aClient) {
        this.theServer = theServer;
        this.aClient = aClient;
    }

    @Override
    public void run() {

        // Auto solve the level and send it to the client
        if (theServer.isAutoSolve()) {
            theServer.handleConnectedClient(aClient);
        }
        // Need approve from the server admin for solution
        else {
            try {
                synchronized (this) {
                    this.wait();
                    if(isDisconnect)
                        throw new InterruptedException();
                    else
                        theServer.handleConnectedClient(aClient);
                }
            } catch (InterruptedException e) {
                System.out.println("disconnecting client..");
                try {
                    aClient.getInputStream().close();
                    aClient.getOutputStream().close();
                    aClient.close();
                } catch (IOException e1) {System.out.println(e1.getMessage());}
            }
        }
    }

    public boolean isDisconnect() {
        return isDisconnect;
    }

    public void setDisconnect(boolean disconnect) {
        isDisconnect = disconnect;
    }



}
