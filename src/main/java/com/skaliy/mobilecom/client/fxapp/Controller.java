package com.skaliy.mobilecom.client.fxapp;

import com.skaliy.mobilecom.client.client.Client;
import com.skaliy.mobilecom.client.panes.PaneMain;
import com.skaliy.mobilecom.client.panes.PaneTariff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Controller {

    @FXML
    private AnchorPane anchorPaneParentMain, anchorPaneParentTariffs;

    @FXML
    private MenuItem menuCreateBackup, menuRestoreBackup, menuHideTray, menuExit,
            menuLogin, menuProfile, menuAbout;

    @FXML
    private SeparatorMenuItem separatorMenuBackup;

    private ObservableList<PaneMain> paneMains;
    private ObservableList<PaneTariff> paneTariffs;

    private Client client;

    public void initialize() throws InterruptedException {
        client = new Client("localhost", 7777);
        Thread thread = new Thread(client);
        thread.start();

        while (true) {
            try {
                if (client.isOpen())
                    break;
            } catch (NullPointerException e) {
                Thread.sleep(100);
            }
        }

        setPaneMains();
        setPaneTariffs();

    }

    private void setPaneMains() {
        ArrayList<String[]> records = client.query("get_news");

        paneMains = FXCollections.observableArrayList();

        for (String[] record : records)
            paneMains.add(new PaneMain(record[1], record[2]));

        anchorPaneParentMain.getChildren().addAll(paneMains);
        anchorPaneParentMain.setPrefHeight(PaneMain.getHeightPaneMain());
    }

    private void setPaneTariffs() {
        ArrayList<String[]> records = client.query("get_tariffs");

        paneTariffs = FXCollections.observableArrayList();

        for (String[] record : records)
            paneTariffs.add(new PaneTariff(record[1], Double.parseDouble(record[2].substring(1)), record[3]));

        anchorPaneParentTariffs.getChildren().addAll(paneTariffs);
        anchorPaneParentTariffs.setPrefHeight(PaneTariff.getHeightPaneTariff());
    }

}