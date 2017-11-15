package com.skaliy.mobilecom.client.fxapp;

import com.skaliy.mobilecom.client.client.Client;
import com.skaliy.mobilecom.client.panes.PaneOrder;
import com.skaliy.mobilecom.client.panes.PaneRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;

import static com.skaliy.mobilecom.client.panes.PaneRecord.*;

public class Controller {

    @FXML
    public Button buttonGetTariffs, buttonGetOffers;

    @FXML
    private AnchorPane anchorPaneParentMain, anchorPaneParentTariffs,
            anchorPaneParentPhones, anchorPaneParentOrder;

    @FXML
    private MenuItem menuCreateBackup, menuRestoreBackup, menuHideTray, menuExit,
            menuLogin, menuProfile, menuAbout;

    @FXML
    private SeparatorMenuItem separatorMenuBackup;

    private ObservableList<PaneRecord> listPaneRecordsMain, listPaneRecordsTariffs,
            listPaneRecordsPhones, listPaneRecordsOrder;

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

        setAnchorPaneParent(anchorPaneParentMain, listPaneRecordsMain,
                "get_news", PANE_MAIN, false);
        setAnchorPaneParent(anchorPaneParentTariffs, listPaneRecordsTariffs,
                "get_tariff_", PANE_TARIFF, true);
        setAnchorPaneParent(anchorPaneParentPhones, listPaneRecordsPhones,
                "get_phones", PANE_PHONE, false);

        buttonGetTariffs.setOnAction(event -> {
            setAnchorPaneParent(anchorPaneParentTariffs, listPaneRecordsTariffs,
                    "get_tariff_", PANE_TARIFF, true);
        });
        buttonGetOffers.setOnAction(event -> {
            setAnchorPaneParent(anchorPaneParentTariffs, listPaneRecordsTariffs,
                    "get_offers", PANE_OFFER, false);
        });

    }

    private void setAnchorPaneParent(AnchorPane paneParent, ObservableList<PaneRecord> listPaneRecords,
                                     String query, final int PANE, boolean index) {
        paneParent.getChildren().clear();
        listPaneRecords = FXCollections.observableArrayList();

        if (!index) {
            for (String[] record : client.query(query)) {
                PaneRecord paneRecord = new PaneRecord(PANE, record);
                paneParent.getChildren().add(paneRecord);
                listPaneRecords.add(paneRecord);
            }
        } else {
            int size = Integer.parseInt(client.query(
                    query.substring(0, query.length() - 1)
                            .concat("s_count")).get(0)[0]);
            for (int i = 1; i <= size; i++) {
                PaneRecord paneRecord = new PaneRecord(PANE, client.query(query + i).get(0));
                paneParent.getChildren().add(paneRecord);
                listPaneRecords.add(paneRecord);
            }
        }
        paneParent.setPrefHeight(PaneRecord.getAndReplaceHeight());

        addOrder(listPaneRecords);
    }

    private void addOrder(ObservableList<PaneRecord> paneRecords) {

        ObservableList<PaneOrder> listPaneOrders = FXCollections.observableArrayList();

        for (int i = 0; i < paneRecords.size(); i++) {
            int finalI = i;

            paneRecords.get(i).getChildren().get(paneRecords.get(i).getIndexLabelOrder()).setOnMouseClicked(event -> {

                PaneOrder paneOrder = new PaneOrder(paneRecords.get(finalI));
                listPaneOrders.add(paneOrder);
                anchorPaneParentOrder.getChildren().add(paneOrder);
                anchorPaneParentOrder.setPrefHeight(paneOrder.getLayoutYNextPane());

                paneOrder.getChildren().get(paneOrder.getIndexThisCancel()).setOnMouseClicked(event1 -> {

                    double layoutYNextPane = paneOrder.getLayoutYNextPane() - paneOrder.getPrefHeight() - 10;
                    anchorPaneParentOrder.setPrefHeight(layoutYNextPane);
                    paneOrder.setLayoutYNextPane(layoutYNextPane);

                    anchorPaneParentOrder.getChildren().remove(paneOrder);
                    listPaneOrders.remove(paneOrder);

                    for (int j = 0; j < listPaneOrders.size(); j++) {
                        listPaneOrders.get(j).setThisRecord(j);
                    }

                    paneOrder.setRecords(paneOrder.getRecords() - 1);

                    for (int j = paneOrder.getThisRecord(); j < paneOrder.getRecords(); j++) {
                        anchorPaneParentOrder.getChildren().get(j).setLayoutY(
                                anchorPaneParentOrder.getChildren().get(j).getLayoutY() - paneOrder.getPrefHeight() - 10);
                    }

                });

            });
        }
    }

/*
    private void setAnchorPaneParent(AnchorPane paneParent, String query, int PANE, int... index) {
        paneParent.getChildren().clear();

        for (int i = 0; i < index.length; i++) {
            paneParent.getChildren().add(new PaneRecord(PANE, client.query(query + index[i]).get(0)));
        }

        paneParent.setPrefHeight(PaneRecord.getAndReplaceHeight());
    }*/

}