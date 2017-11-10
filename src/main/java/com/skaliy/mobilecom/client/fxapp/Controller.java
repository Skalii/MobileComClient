package com.skaliy.mobilecom.client.fxapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.skaliy.netty.nc.Client;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.ArrayList;

public class Controller {

    @FXML
    private MenuItem menuCreateBackup, menuRestoreBackup, menuHideTray, menuExit,
            menuLogin, menuProfile, menuAbout;

    @FXML
    private SeparatorMenuItem separatorMenuBackup;

    @FXML
    private JFXTextField textTest;

    @FXML
    private JFXTextArea textAreaTest;

    @FXML
    private JFXButton buttonTestSelect, buttonTestUpdate;

    public void initialize() {
        Client client = new Client("localhost", 7777);
        Thread thread = new Thread(client);
        thread.start();

        buttonTestSelect.setOnAction(event -> {
            ArrayList<String[]> queryResult = client.query(textTest.getText());

            textAreaTest.appendText("[CLIENT] - read result:\n");
            for (String[] anArrayList : queryResult) {
                for (String anAnArrayList : anArrayList) {
                    textAreaTest.appendText(anAnArrayList + "; ");
                }
                textAreaTest.appendText("\n");
            }

        });

        buttonTestUpdate.setOnAction(event -> {
            boolean queryResult = client.query(false, textTest.getText());

            textAreaTest.appendText("[CLIENT] - query state: ");
            textAreaTest.appendText(String.valueOf(queryResult) + "\n");
        });

    }

}
