package com.skaliy.mobilecom.client.fxapp;

import com.skaliy.mobilecom.client.client.Client;
import com.skaliy.mobilecom.client.panes.PaneOrder;
import com.skaliy.mobilecom.client.panes.PaneRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.skaliy.mobilecom.client.panes.PaneRecord.*;

public class Controller {

    @FXML
    public TextField textClientFName, textClientLName, textClientPName, textClientPNumber, textClientEmail,
            textSearchPhones, textSearchTariffs, textSearchOffers;

    @FXML
    public Label labelOrderPrice;

    @FXML
    public Button buttonOrderAccept, buttonOrderClear;

    @FXML
    public ComboBox<String> comboOrderEmployee,
            comboSearchManufacturer, comboSearchColor, comboSearchOS, comboSearchRAM, comboSearchROM,
            comboSearchMemoryCard, comboSearchSIM, comboSearchProcessor, comboSearchBatary,
            comboSearchDiagonal, comboSearchResolution, comboSearchCameraMain, comboSearchCameraFront,
            comboSearchTariffs, comboSearchOffers;

    @FXML
    private AnchorPane anchorPaneParentMain, anchorPaneParentTariffs, anchorPaneParentOffers,
            anchorPaneParentPhones, anchorPaneParentOrder;

    @FXML
    private MenuItem menuHideTray, menuExit, menuLogin, menuAbout;

    private ObservableList<PaneRecord> listPaneRecordsMain, listPaneRecordsTariffs, listPaneRecordsOffers,
            listPaneRecordsPhones;

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

        menuExit.setOnAction(event -> {
            System.exit(0);
        });
        menuHideTray.setOnAction(event -> {
            Main.getStage().hide();
        });
        menuLogin.setOnAction(event -> {
            com.skaliy.mobilecom.client.fxapp.admin.Main mainAdmin
                    = new com.skaliy.mobilecom.client.fxapp.admin.Main();
            try {
                mainAdmin.start(new Stage());
                mainAdmin.setClient(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        listPaneRecordsMain = setAnchorPaneParent(
                anchorPaneParentMain, "get_news", PANE_MAIN, false);
        listPaneRecordsTariffs = setAnchorPaneParent(
                anchorPaneParentTariffs, "get_tariff_i-", PANE_TARIFF, true);
        listPaneRecordsOffers = setAnchorPaneParent(
                anchorPaneParentOffers, "get_offers", PANE_OFFER, false);
        listPaneRecordsPhones = setAnchorPaneParent(
                anchorPaneParentPhones, "get_phones", PANE_PHONE, false);

        search(textSearchPhones, anchorPaneParentPhones, listPaneRecordsPhones,
                "get_phone_p-", PANE_PHONE);
        search(textSearchTariffs, anchorPaneParentTariffs, listPaneRecordsTariffs,
                "get_tariff_p-", PANE_TARIFF);
        search(textSearchOffers, anchorPaneParentOffers, listPaneRecordsOffers,
                "get_offer_p-", PANE_OFFER);

        setComboItems(comboSearchTariffs, "Название тарифа", "get_tariffs_title");

        setComboItems(comboSearchOffers, "Название услуги", "get_offers_title");

        setComboItems(comboSearchManufacturer, "Производитель", "get_m_names");
        setComboItems(comboSearchColor, "Цвет", "get_phones_colors");
        setComboItems(comboSearchOS, "ОС", "get_pd_os");
        setComboItems(comboSearchRAM, "Оперативная память", "get_pd_ram");
        setComboItems(comboSearchROM, "Встроенная память", "get_pd_rom");
        setComboItems(comboSearchMemoryCard, "Карта памяти", "get_pd_memory_card");
        setComboItems(comboSearchSIM, "Количество SIM-карт", "get_pd_sim");
        setComboItems(comboSearchProcessor, "Процессор", "get_pd_processor");
        setComboItems(comboSearchBatary, "Батарея", "get_pd_batary");
        setComboItems(comboSearchDiagonal, "Диагональ", "get_pd_diagonal");
        setComboItems(comboSearchResolution, "Разрешение", "get_pd_resolution");
        setComboItems(comboSearchCameraMain, "Основная камера", "get_pd_camera_main");
        setComboItems(comboSearchCameraFront, "Фронтальная камера", "get_pd_camera_front");

        search(textSearchTariffs, anchorPaneParentTariffs, listPaneRecordsTariffs,
                "get_tariff_c-", PANE_TARIFF, FXCollections.observableArrayList(comboSearchTariffs));
        search(textSearchOffers, anchorPaneParentOffers, listPaneRecordsOffers,
                "get_offer_c-", PANE_OFFER, FXCollections.observableArrayList(comboSearchOffers));
        search(textSearchPhones, anchorPaneParentPhones, listPaneRecordsPhones,
                "get_phone_c-", PANE_PHONE, FXCollections.observableArrayList(
                        comboSearchManufacturer, comboSearchColor, comboSearchOS, comboSearchRAM, comboSearchROM,
                        comboSearchMemoryCard, comboSearchSIM, comboSearchProcessor, comboSearchBatary, comboSearchDiagonal,
                        comboSearchResolution, comboSearchCameraMain, comboSearchCameraFront));

        setComboItems(comboOrderEmployee, "Консультант", "get_e_names");
        buttonOrderClear.setOnAction(event -> {
            clearOrders();
        });
        buttonOrderAccept.setOnAction(event -> {

            if (textClientFName.getText().isEmpty() || textClientLName.getText().isEmpty()
                    || textClientPName.getText().isEmpty() || textClientPNumber.getText().isEmpty()
                    || textClientEmail.getText().isEmpty()) {

                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Заполните обязательные поля ввода *\n" +
                                "(ФИО, номер телефона, email)");

                return;
            }

            if (!listPaneOrders.isEmpty()) {
                double amount = 0.00;
                String employee = comboOrderEmployee.getSelectionModel().getSelectedItem();
                int idEmployee = comboOrderEmployee.getSelectionModel().getSelectedIndex();

                if (idEmployee != 0) {
                    try {
                        idEmployee = Integer.parseInt(client.query("get_id_employee_p-" + employee).get(0)[0]);
                    } catch (NumberFormatException e) {
                        idEmployee = 0;
                    }

                }

                for (PaneOrder listPaneOrder : listPaneOrders) {
                    amount += listPaneOrder.getPrice() * listPaneOrder.getUnits();
                }

                boolean stateQuerySale = client.query(false,
                        "add_sale," + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                + "," + amount
                                + (idEmployee != 0 ? ",id" + idEmployee : "") + ",FALSE,"
                                + textClientLName.getText() + " " + textClientFName.getText() + " " + textClientPName.getText()
                                + "," + textClientPNumber.getText() + "," + textClientEmail.getText());

                if (stateQuerySale) {
                    int idLastSale = Integer.parseInt(client.query("get_last_sale").get(0)[0]);

                    int p = 0, t = 0;
                    for (int i = 0; i < listPaneOrders.size(); i++) {
                        if (listPaneOrders.get(i).getPaneRecord().getTHIS_PANE() == PANE_PHONE) {
                            ++p;
                        } else if (listPaneOrders.get(i).getPaneRecord().getTHIS_PANE() == PANE_TARIFF) {
                            ++t;
                        }
                    }

                    boolean[] stateQueryUnionPhones = new boolean[p], stateQueryUnionTariffs = new boolean[t];

                    for (int i = 0, j = 0, k = 0; i < listPaneOrders.size(); i++) {

                        if (listPaneOrders.get(i).getPaneRecord().getTHIS_PANE() == PANE_PHONE) {
                            stateQueryUnionPhones[j++] = client.query(false,
                                    "add_union_phone," + idLastSale
                                            + "," + listPaneOrders.get(i).getPaneRecord().getRecord()
                                            + "," + listPaneOrders.get(i).getUnits());

                        } else if (listPaneOrders.get(i).getPaneRecord().getTHIS_PANE() == PANE_TARIFF) {
                            stateQueryUnionTariffs[k++] = client.query(false,
                                    "add_union_tariff," + idLastSale
                                            + "," + listPaneOrders.get(i).getPaneRecord().getRecord()
                                            + "," + listPaneOrders.get(i).getUnits());
                        }

                    }

                    clearOrders();

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

    private ObservableList<PaneRecord> setAnchorPaneParent(AnchorPane paneParent,
                                                           String query, final int PANE, boolean index) {

        ObservableList<PaneRecord> listPaneRecords = FXCollections.observableArrayList();
        paneParent.getChildren().clear();
        double prefHeight = 0.00;

        if (!index) {
            ArrayList<String[]> records = client.query(query);

            if (!Objects.equals(records.get(0)[0], "null")) {
                for (String[] record : records) {
                    PaneRecord paneRecord = new PaneRecord(PANE, record);
                    paneParent.getChildren().add(paneRecord);
                    listPaneRecords.add(paneRecord);
                }
                prefHeight = PaneRecord.getAndReplaceHeight();
            }
        } else {
            int size = Integer.parseInt(client.query(query
                    .replace("_i-", "s_count")).get(0)[0]);
            if (size != 0) {
                for (int i = 1; i <= size; i++) {
                    PaneRecord paneRecord = new PaneRecord(PANE, client.query(query + i).get(0));
                    paneParent.getChildren().add(paneRecord);
                    listPaneRecords.add(paneRecord);
                }
                prefHeight = PaneRecord.getAndReplaceHeight();
            }

        }
        paneParent.setPrefHeight(prefHeight);

        setOrders(listPaneRecords);
        return listPaneRecords;
    }

    private void search(TextField textSearch, AnchorPane paneParent, ObservableList<PaneRecord> listPaneRecords,
                        String query, final int PANE) {

        textSearch.setOnKeyReleased(event -> {

            paneParent.getChildren().clear();
            listPaneRecords.clear();

            String search = textSearch.getText();

            if (Objects.equals(search, "")) {
                if (Objects.equals(query, "get_tariff_p-")) {
                    setAnchorPaneParent(paneParent, query
                            .replace("_p-", "_i-"), PANE, true);
                } else {
                    setAnchorPaneParent(paneParent, query
                            .replace("_p-", "s"), PANE, false);
                }
            } else {
                String querySearch = query.concat(search);

                setAnchorPaneParent(paneParent, querySearch, PANE, false);
            }

        });

    }

    private void search(TextField textSearch, AnchorPane paneParent, ObservableList<PaneRecord> listPaneRecords,
                        String query, final int PANE, ObservableList<ComboBox<String>> listCombo) {

        for (int i = 0; i < listCombo.size(); i++) {

            listCombo.get(i).setOnAction(event -> {

                paneParent.getChildren().clear();
                listPaneRecords.clear();

                String search = query;

                for (ComboBox<String> aListCombo : listCombo) {

                    if (aListCombo.getValue() != null
                            && !aListCombo.getSelectionModel().isSelected(0)) {

                        if (!Objects.equals(search, query)) {
                            search = search.concat(";");
                        }

                        search = search.concat(
                                aListCombo.getItems().get(0)
                                        + " = '" + aListCombo.getSelectionModel().getSelectedItem() + "'");
                    }
                }

                if (!Objects.equals(search, query)) {
                    textSearch.clear();
                    textSearch.setDisable(true);
                    setAnchorPaneParent(paneParent, search, PANE, false);
                } else {
                    textSearch.setDisable(false);
                    if (!Objects.equals(search, "get_tariff_c-")) {
                        setAnchorPaneParent(paneParent, query.replace("_c-", "s"), PANE, false);
                    } else {
                        setAnchorPaneParent(paneParent, search.replace("_c-", "_i-"), PANE, true);
                    }
                }

            });
        }

    }

    private void setOrders(ObservableList<PaneRecord> paneRecords) {

        for (int i = 0; i < paneRecords.size(); i++) {
            int finalI = i;

            paneRecords.get(i).getChildren().get(paneRecords.get(i).getIndexLabelOrder()).setOnMouseClicked(event -> {

                for (int j = 0; j < listPaneOrders.size(); j++) {
                    if (paneRecords.get(finalI).getTHIS_PANE() == listPaneOrders.get(j).getPaneRecord().getTHIS_PANE()
                            && paneRecords.get(finalI).getRecord() == listPaneOrders.get(j).getPaneRecord().getRecord()) {
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

    private void clearOrders() {
        if (!anchorPaneParentOrder.getChildren().isEmpty()) {
            listPaneOrders.get(0).setLayoutYNextPane(10);
            listPaneOrders.get(0).setRecords(0);
            listPaneOrders.clear();
            anchorPaneParentOrder.getChildren().clear();
            anchorPaneParentOrder.setPrefHeight(0);

            buttonOrderAccept.setDisable(true);
            buttonOrderClear.setDisable(true);

            textClientLName.clear();
            textClientFName.clear();
            textClientPName.clear();
            textClientPNumber.clear();
            textClientEmail.clear();
            comboOrderEmployee.getSelectionModel().clearSelection();
        }
    }

}