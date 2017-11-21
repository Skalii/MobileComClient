package com.skaliy.mobilecom.client.fxapp.login;

import com.skaliy.mobilecom.client.connection.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogin extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        stage = primaryStage;
    }

    static Stage getStage() {
        return stage;
    }

    public void setClient(Client client) {
       ControllerLogin.setClient(client);
    }

}
