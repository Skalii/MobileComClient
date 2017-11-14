package com.skaliy.mobilecom.client.fxapp;

import com.skaliy.mobilecom.client.client.Client;
import com.skaliy.mobilecom.client.panes.PaneParent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;

import static com.skaliy.mobilecom.client.panes.PaneParent.*;

public class Controller {

    @FXML
    public Button buttonGetTariffs, buttonGetOffers;

    @FXML
    private AnchorPane anchorPaneParentMain, anchorPaneParentTariffs, anchorPaneParentPhones;

    @FXML
    private MenuItem menuCreateBackup, menuRestoreBackup, menuHideTray, menuExit,
            menuLogin, menuProfile, menuAbout;

    @FXML
    private SeparatorMenuItem separatorMenuBackup;

    private Client client;

    public void initialize() {
        client = new Client("localhost", 7777);
        Thread thread = new Thread(client);
        thread.start();

        while (!client.isOpen()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setPaneParent(anchorPaneParentMain, "get_news", PANE_MAIN, false);
        setPaneParent(anchorPaneParentTariffs, "get_tariff_", PANE_TARIFF, true);
        setPaneParent(anchorPaneParentPhones, "get_phones", PANE_PHONE, false);

        buttonGetTariffs.setOnAction(event -> {
            setPaneParent(anchorPaneParentTariffs, "get_tariff_", PANE_TARIFF, true);
        });
        buttonGetOffers.setOnAction(event -> {
            setPaneParent(anchorPaneParentTariffs, "get_offers", PANE_OFFER, false);
        });
    }

    private void setPaneParent(AnchorPane paneParent, String query, final int PANE, boolean index) {
        paneParent.getChildren().clear();

        if (!index) {
            for (String[] record : client.query(query)) {
                paneParent.getChildren().add(new PaneParent(PANE, record));
            }
        } else {
            int size = Integer.parseInt(client.query(
                    query.substring(0, query.length() - 1)
                            .concat("s_count")).get(0)[0]);
            for (int i = 1; i <= size; i++) {
                paneParent.getChildren().add(new PaneParent(PANE, client.query(query + i).get(0)));
            }
        }
        paneParent.setPrefHeight(PaneParent.getAndReplaceHeight());
    }
/*
    private void setPaneParent(AnchorPane paneParent, String query, int PANE, int... index) {
        paneParent.getChildren().clear();

        for (int i = 0; i < index.length; i++) {
            paneParent.getChildren().add(new PaneParent(PANE, client.query(query + index[i]).get(0)));
        }

        paneParent.setPrefHeight(PaneParent.getAndReplaceHeight());
    }*/

}