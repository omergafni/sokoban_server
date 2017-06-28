package com.server.model;

import java.io.IOException;

public interface ServerModel {

    void setAutoSolve(boolean bool);
    void runServer() throws IOException;
    void awakeClient(int clientThreadID);
    void disconnectClient(int clientThreadID);

}
