package com.server.view_model;

import com.server.model.ServerModel;
import com.server.model.client.ConnectedClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;
import java.util.Observer;

/**
 * ServerViewModel is the view-model layer of the sokoban server application
 */
public class ServerViewModel extends Observable implements Observer {

    private ServerModel model;
    private ObservableList<ConnectedClient> data = FXCollections.observableArrayList();
    private SimpleStringProperty selectedMode;
    private SimpleStringProperty approveId;
    private SimpleStringProperty isRefused;

    public ServerViewModel(ServerModel model) {
        this.model = model;
        this.selectedMode = new SimpleStringProperty();
        this.approveId = new SimpleStringProperty();
        this.isRefused = new SimpleStringProperty();
        this.selectedMode.addListener((observable, oldValue, newValue) -> {
            if(newValue.contains("auto"))
                model.setAutoSolve(true);
            else
                model.setAutoSolve(false);
        });
        this.approveId.addListener((observable, oldValue, newValue) ->
                model.awakeClient(Integer.parseInt(newValue)));
        this.isRefused.addListener((observable, oldValue, newValue) ->
                model.disconnectClient(Integer.parseInt(newValue)));
    }

    @Override
    public void update(Observable o, Object arg) {
        data.add((ConnectedClient)arg);
        setChanged();
        notifyObservers(data);
    }

    public String getSelectedMode() {
        return selectedMode.get();
    }

    public SimpleStringProperty selectedModeProperty() {
        return selectedMode;
    }

    public void setSelectedMode(String selectedMode) {
        this.selectedMode.set(selectedMode);
    }

    public String getApproveId() {
        return approveId.get();
    }

    public SimpleStringProperty approveIdProperty() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId.set(approveId);
    }

    public String getIsRefused() {
        return isRefused.get();
    }

    public SimpleStringProperty isRefusedProperty() {
        return isRefused;
    }
}
