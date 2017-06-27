package com.server.view_model;

import com.server.model.ServerModel;
import com.server.model.client.ConnectedClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;
import java.util.Observer;

public class ServerViewModel extends Observable implements Observer {

    private ServerModel model;
    private ObservableList<ConnectedClient> data = FXCollections.observableArrayList();
    private SimpleStringProperty selectedMode;

    public ServerViewModel(ServerModel model) {
        this.model = model;
        this.selectedMode = new SimpleStringProperty();
        this.selectedMode.addListener((observable, oldValue, newValue) -> {
            if(newValue.contains("auto")){
                model.setAutoSolve(true);
            }
            else{
                model.setAutoSolve(false);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        data.add((ConnectedClient)arg);
        setChanged();
        notifyObservers(data);
    }

    public SimpleStringProperty getSelectedMode() {
        return this.selectedMode;
    }


}
