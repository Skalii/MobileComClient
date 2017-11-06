package com.skaliy.mobilecom.fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Салон мобильной связи");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
