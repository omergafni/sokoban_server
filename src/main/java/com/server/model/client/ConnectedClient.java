package com.server.model.client;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/**
 * ConnectedClient is a client property class that represents the connected client data.
 * This is used by the GUI layer to represents a connected client.
 */
public class ConnectedClient {

    private final SimpleStringProperty clientId;
    private final SimpleStringProperty time;
    private final SimpleStringProperty status;
    private final SimpleObjectProperty<Button> approveButton;
    private final SimpleObjectProperty<Button> refuseButton;
    private final int threadId;

    /**
     * Constructor
     * @param clientId is the key of the object
     * @param time is the time that the user made a connection request
     * @param status is the current server status that define if the specific user will get the solution automatically or by admin discretion
     * @param threadId is the id for the client thread. used by the server thread map to access a thread
     */
    public ConnectedClient(SimpleStringProperty clientId, SimpleStringProperty time,
                           SimpleStringProperty status, int threadId) {
        this.clientId = clientId;
        this.time = time;
        this.status = status;
        this.threadId = threadId;
        this.approveButton = new SimpleObjectProperty<>(new Button("Approve"));
        this.refuseButton = new SimpleObjectProperty<>(new Button("Refuse"));
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

    public Button getRefuseButton() {
        return refuseButton.get();
    }

    public SimpleObjectProperty<Button> refuseButtonProperty() {
        return refuseButton;
    }

    public void setRefuseButton(Button refuseButton) {
        this.refuseButton.set(refuseButton);
    }

    public int getThreadId() {
        return threadId;
    }

    public Button getApproveButton() {
        return approveButton.get();
    }

    public SimpleObjectProperty<Button> approveButtonProperty() {
        return approveButton;
    }

    public void setApproveButton(Button approveButton) {
        this.approveButton.set(approveButton);
    }
}
