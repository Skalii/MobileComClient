package com.skaliy.mobilecom.client.fxapp.admin;

import com.skaliy.mobilecom.client.connection.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

//    private Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
        primaryStage.setTitle("Администрирование");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

//        stage = primaryStage;
    }


    /*public static void main(String[] args) {
        launch(args);
    }*/

    public void setClient(Client client) {
        Controller.setClient(client);
    }
}
