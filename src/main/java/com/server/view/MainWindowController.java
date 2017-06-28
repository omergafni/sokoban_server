package com.server.view;

import com.server.model.client.ConnectedClient;
import com.server.view_model.ServerViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

// Our View
public class MainWindowController implements ServerView, Observer, Initializable {

    ServerViewModel vm;
    @FXML private TableView<ConnectedClient> table;
    @FXML private TableColumn<ConnectedClient, String> clientId;
    @FXML private TableColumn<ConnectedClient, String> time;
    @FXML private TableColumn<ConnectedClient, String> status;
    @FXML private TableColumn<ConnectedClient, Button> refuse;
    @FXML private TableColumn<ConnectedClient, Button> approve;
    @FXML private RadioButton autoSolve;
    @FXML private RadioButton manApprove;
    @FXML private ToggleGroup mode;
    private StringProperty selectedMode;
    private StringProperty approveId;
    private StringProperty isRefused;


    public MainWindowController() {
        this.selectedMode = new SimpleStringProperty("auto");
        this.approveId = new SimpleStringProperty();
        this.isRefused = new SimpleStringProperty();
    }

    @Override
    public void update(Observable o, Object arg) {

        table.setItems((ObservableList<ConnectedClient>)arg);
        ConnectedClient client = ((ObservableList<ConnectedClient>) arg).get(((ObservableList<ConnectedClient>) arg).size()-1);
        // Approve is clicked
        approve.getCellData(client).setOnAction((e)->{
            this.approveId.setValue(Integer.toString(client.getThreadId()));
            client.setStatus("Approved");
            approve.getCellData(client).setText("approved");
            approve.getCellData(client).setDisable(true);
            refuse.getCellData(client).setText("refuse");
            refuse.getCellData(client).setDisable(true);
        });
        // Refuse is clicked
        refuse.getCellData(client).setOnAction((e)->{
            this.isRefused.setValue(Integer.toString(client.getThreadId()));
            client.setStatus("Refused");
            approve.getCellData(client).setText("approved");
            approve.getCellData(client).setDisable(true);
            refuse.getCellData(client).setText("refuse");
            refuse.getCellData(client).setDisable(true);
        });
        if(selectedMode.get().contains("auto")){
            client.setStatus("Disconnected (got solution!)");
            approve.getCellData(client).setVisible(false);
            refuse.getCellData(client).setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        approve.setCellValueFactory(new PropertyValueFactory<>("approveButton"));
        refuse.setCellValueFactory(new PropertyValueFactory<>("refuseButton"));

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
        vm.selectedModeProperty().bind(this.selectedMode);
        vm.approveIdProperty().bind(this.approveId);
        vm.isRefusedProperty().bind(this.isRefused);
    }


}
