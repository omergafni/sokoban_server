package com.server.model.client;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
    void handleClient(InputStream fromClient, OutputStream toClient);
}
