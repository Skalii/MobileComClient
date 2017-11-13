package com.skaliy.mobilecom.client.fxapp;

import com.skaliy.mobilecom.client.client.Client;
import com.skaliy.mobilecom.client.panes.PaneParent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;

import static com.skaliy.mobilecom.client.panes.PaneParent.PANE_MAIN;
import static com.skaliy.mobilecom.client.panes.PaneParent.PANE_OFFER;
import static com.skaliy.mobilecom.client.panes.PaneParent.PANE_TARIFF;

public class Controller {

    @FXML
    public Button buttonGetTariffs, buttonGetOffers;

    @FXML
    private AnchorPane anchorPaneParentMain, anchorPaneParentTariffs;

    @FXML
    private MenuItem menuCreateBackup, menuRestoreBackup, menuHideTray, menuExit,
            menuLogin, menuProfile, menuAbout;

    @FXML
    private SeparatorMenuItem separatorMenuBackup;

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

        setPaneParent(anchorPaneParentMain, "get_news", PANE_MAIN);
        setPaneParent(anchorPaneParentTariffs, "get_tariffs", PANE_TARIFF);

        buttonGetTariffs.setOnAction(event -> {
            setPaneParent(anchorPaneParentTariffs, "get_tariffs", PANE_TARIFF);
        });
        buttonGetOffers.setOnAction(event -> {
            setPaneParent(anchorPaneParentTariffs, "get_offers", PANE_OFFER);
        });
    }

    private void setPaneParent(AnchorPane paneParent, String query, int PANE) {
        paneParent.getChildren().clear();

        for (String[] record : client.query(query))
            paneParent.getChildren().add(new PaneParent(PANE, record));

        paneParent.setPrefHeight(PaneParent.getAndReplaceHeight());
    }

}