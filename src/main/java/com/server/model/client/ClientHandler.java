package com.server.model.client;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * ClientHandler is the interface that used by the server to handle connected clients
 */
public interface ClientHandler {
    /**
     * Handle connected clients
     * @param fromClient is the input stream of the connected client
     * @param toClient is the output stream of the connected client
     */
    void handleClient(InputStream fromClient, OutputStream toClient);
}
