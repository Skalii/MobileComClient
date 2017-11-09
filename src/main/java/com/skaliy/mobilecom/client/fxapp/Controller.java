package com.skaliy.mobilecom.client.fxapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.skaliy.nc.Client;
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
    private JFXButton buttonTest;

    public void initialize() {
        Client client = new Client("localhost", 7777);
        Thread thread = new Thread(client);
        thread.start();

        buttonTest.setOnAction(event -> {
            client.setQuery(textTest.getText() + "\r\n");

            ArrayList<String[]> arrayList = client.getResult();

            for (String[] anArrayList : arrayList) {
                for (String anAnArrayList : anArrayList) {
                    textAreaTest.setText(textAreaTest.getText() + anAnArrayList + " ");
                    System.out.print(anAnArrayList + " ");
                }
                textAreaTest.setText(textAreaTest.getText() + "\n");
                System.out.println();
            }

        });

    }

}
