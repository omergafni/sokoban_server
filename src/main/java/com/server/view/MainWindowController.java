package com.server.view;

import com.server.model.client.ConnectedClient;
import com.server.view_model.ServerViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

// Our View
public class MainWindowController implements ServerView, Observer, Initializable {

    ServerViewModel vm;
    @FXML private TableView<ConnectedClient> table;
    @FXML private TableColumn<ConnectedClient, String> clientId;
    @FXML private TableColumn<ConnectedClient, String> level;
    @FXML private TableColumn<ConnectedClient, String> time;
    @FXML private TableColumn<ConnectedClient, String> status;
    @FXML private TableColumn<ConnectedClient, String> action;
    @FXML private RadioButton autoSolve;
    @FXML private RadioButton manApprove;
    @FXML private ToggleGroup mode;

    private StringProperty selectedMode;

    public MainWindowController() {
        this.selectedMode = new SimpleStringProperty();
    }

    @Override
    public void update(Observable o, Object arg) {

        table.setItems((ObservableList<ConnectedClient>)arg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        level.setCellValueFactory(new PropertyValueFactory<>("level"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        /**
         * This is the listener for the Auto-Solve option in the admin control panel
         */
        mode.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            // "Manual Approve" or "Auto Solve"
            if (new_toggle.toString().contains("Manual")) {
                selectedMode.setValue("manual");
            } else {
                selectedMode.setValue("auto");
            }

        });
    }

    public void setViewModel(ServerViewModel vm){
        this.vm = vm;
        vm.getSelectedMode().bind(this.selectedMode);

    }

}
