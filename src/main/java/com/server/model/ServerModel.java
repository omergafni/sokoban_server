package com.server.model;

import java.io.IOException;

/**
 * ServerModel is the abstraction for the model layer
 */
public interface ServerModel {

    void setAutoSolve(boolean bool);
    void runServer() throws IOException;
    void awakeClient(int clientThreadID);
    void disconnectClient(int clientThreadID);

}
