package com.server.model.client;

import javafx.beans.property.SimpleStringProperty;

public class ConnectedClient {

    private final SimpleStringProperty clientId;
    private final SimpleStringProperty level;
    private final SimpleStringProperty time;
    private final SimpleStringProperty status;

    public ConnectedClient(SimpleStringProperty clientId, SimpleStringProperty level, SimpleStringProperty time, SimpleStringProperty status) {
        this.clientId = clientId;
        this.level = level;
        this.time = time;
        this.status = status;
    }

    public String getClientId() {
        return clientId.get();
    }

    public SimpleStringProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId.set(clientId);
    }

    public String getLevel() {
        return level.get();
    }

    public SimpleStringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
