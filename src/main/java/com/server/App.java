package com.server;

import com.server.model.MyServerModel;
import com.server.model.client.SokobanClientHandler;
import com.server.view.MainWindowController;
import com.server.view_model.ServerViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = fxmlLoader.load();

        MainWindowController myView = fxmlLoader.getController();
        MyServerModel myServerModel = new MyServerModel(5555,new SokobanClientHandler());
        ServerViewModel myViewModel = new ServerViewModel(myServerModel);
        myView.setViewModel(myViewModel);

        myViewModel.addObserver(myView);
        myServerModel.addObserver(myViewModel);
        new Thread(()->{
            try {
                myServerModel.runServer();}
            catch (IOException e) {e.printStackTrace();}
        }).start();

        //primaryStage.setOnCloseRequest(event -> sokoView.exit());
        primaryStage.setScene(new Scene(root,600,600));
        primaryStage.setTitle("Sokoban Solution Server");
        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
