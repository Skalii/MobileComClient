package com.skaliy.mobilecom.client.fxapp;

import com.skaliy.mobilecom.client.client.Client;
import com.skaliy.mobilecom.client.panes.PaneOrder;
import com.skaliy.mobilecom.client.panes.PaneRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.skaliy.mobilecom.client.panes.PaneRecord.*;

public class Controller {

    @FXML
    public Button buttonGetTariffs, buttonGetOffers;

    @FXML
    public TextField textClientFName, textClientLName, textClientPName,
            textClientPNumber, textClientEmail;

    @FXML
    public Label labelOrderPrice;

    @FXML
    public Button buttonOrderAccept, buttonOrderClear;

    @FXML
    public ComboBox<String> comboOrderEmployee;

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

    private ObservableList<PaneOrder> listPaneOrders = FXCollections.observableArrayList();

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

        setComboItems(comboOrderEmployee, "Консультант", "get_employees_name");

        buttonGetTariffs.setOnAction(event -> {
            setAnchorPaneParent(anchorPaneParentTariffs, listPaneRecordsTariffs,
                    "get_tariff_", PANE_TARIFF, true);
        });
        buttonGetOffers.setOnAction(event -> {
            setAnchorPaneParent(anchorPaneParentTariffs, listPaneRecordsTariffs,
                    "get_offers", PANE_OFFER, false);
        });

        buttonOrderClear.setOnAction(event -> {

            if (!anchorPaneParentOrder.getChildren().isEmpty()) {
                listPaneOrders.get(0).setLayoutYNextPane(10);
                listPaneOrders.get(0).setRecords(0);
                listPaneOrders.clear();
                anchorPaneParentOrder.getChildren().clear();
                anchorPaneParentOrder.setPrefHeight(0);

                buttonOrderAccept.setDisable(true);
                buttonOrderClear.setDisable(true);
            }
        });

        buttonOrderAccept.setOnAction(event -> {

            if (!anchorPaneParentOrder.getChildren().isEmpty()) {
                double amount = 0.00;
                String employee = comboOrderEmployee.getSelectionModel().getSelectedItem();
                int idEmployee = 0;

                if (!Objects.equals(employee, "Консультант")) {
                    idEmployee = Integer.parseInt(client.query("get_id_employee_" + employee).get(0)[0]);
                }

                for (PaneOrder listPaneOrder : listPaneOrders) {
                    amount += listPaneOrder.getPrice() * listPaneOrder.getUnits();
                }

                boolean stateQuerySale = client.query(false,
                        "add_sale," + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                + "," + amount
                                + (!Objects.equals(employee, "Консультант") ? ",id" + idEmployee : "")
                                + ",FALSE," + textClientLName + " " + textClientFName + " " + textClientPName
                                + "," + textClientPNumber + "," + textClientEmail);

                if (stateQuerySale) {
                    int idLastSale = Integer.parseInt(client.query("get_last_sale").get(0)[0]);

                    boolean stateQuerySaleDetail = client.query(false, "add_sale_detail," + idLastSale);

                    if (stateQuerySaleDetail) {
                        int idLastSaleDetail = Integer.parseInt(client.query("get_last_sale_detail").get(0)[0]);
// TODO: 15.11.2017
                        boolean stateQueryUnionPhones = client.query(false,
                                "");
                    }

                }

            }

        });

    }

    private void setComboItems(ComboBox<String> comboItems, String selectValue, String query) {
        ArrayList<String[]> records = client.query(query);

        comboItems.getItems().add(selectValue);

        for (String[] record : records) {
            comboItems.getItems().add(record[0]);
        }

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

        setOrders(listPaneRecords);
    }

    private void setOrders(ObservableList<PaneRecord> paneRecords) {

        for (int i = 0; i < paneRecords.size(); i++) {
            int finalI = i;

            paneRecords.get(i).getChildren().get(paneRecords.get(i).getIndexLabelOrder()).setOnMouseClicked(event -> {

                for (int j = 0; j < listPaneOrders.size(); j++) {
                    if (paneRecords.get(finalI).getRecord() == listPaneOrders.get(j).getPaneRecord().getRecord()) {
                        listPaneOrders.get(j).setPriceAndUnits(listPaneOrders.get(j).getUnits() + 1);
                        return;
                    }
                }

                PaneOrder paneOrder = new PaneOrder(paneRecords.get(finalI));

                listPaneOrders.add(paneOrder);
                anchorPaneParentOrder.getChildren().add(paneOrder);
                anchorPaneParentOrder.setPrefHeight(paneOrder.getLayoutYNextPane());

                if (listPaneOrders.size() == 1) {
                    buttonOrderAccept.setDisable(false);
                    buttonOrderClear.setDisable(false);
                }

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

                    if (anchorPaneParentOrder.getChildren().isEmpty()) {
                        anchorPaneParentOrder.setPrefHeight(0);
                        buttonOrderAccept.setDisable(true);
                        buttonOrderClear.setDisable(true);
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