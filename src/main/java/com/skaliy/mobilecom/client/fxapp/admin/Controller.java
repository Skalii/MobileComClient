package com.skaliy.mobilecom.client.fxapp.admin;

import com.skaliy.mobilecom.client.connection.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;

public class Controller {

    @FXML
    public TextField textAdd1, textAdd2, textAdd3, textAdd4, textAdd5,
            textAdd6, textAdd7, textAdd8, textAdd9, textAdd10,
            textAdd11, textAdd12, textAdd13, textAdd14, textAdd15;

    @FXML
    private Button buttonAdd, buttonDelete;

    @FXML
    private ComboBox<String> comboSetTable, comboAdd1, comboAdd2, comboPhoneDetails;

    @FXML
    private TextArea textArea;

    @FXML
    private TableView<String[]> table1;

    private static Client client;

    public void initialize() {

        comboSetTable.getItems().addAll("- выбор таблицы -", "Новости",
                "Продажи", "Сотрудники", "Тарифы", "Услуги", "Телефоны", "Производители");

        comboSetTable.setOnAction(event -> {

            table1.setLayoutY(185);
            table1.setPrefHeight(370);
            table1.setDisable(false);

            clearAndHide(textArea);
            clearAndHide(comboAdd1, comboAdd2, comboPhoneDetails);
            clearAndHide(textAdd1, textAdd2, textAdd3, textAdd4, textAdd5,
                    textAdd6, textAdd7, textAdd8, textAdd9, textAdd10,
                    textAdd11, textAdd12, textAdd13, textAdd14, textAdd15);

            switch (comboSetTable.getSelectionModel().getSelectedIndex()) {

                case 0:
                    table1.setDisable(true);
                    table1.getColumns().clear();
                    table1.getItems().clear();
                    buttonAdd.setDisable(true);
                    buttonDelete.setDisable(true);
                    break;

                case 1:

                    setTable("get_news",
                            new String[]{"", "Название", "Текст"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd3}, new int[]{1}, new String[]{"Название"},
                            null, null, null,
                            textArea, 2, "Текст");
                    setDisableButton(buttonAdd, buttonDelete, new TextField[]{textAdd3});

                    editCellTable("news");
                    deleteRecord("news");
                    addRecord("news", new TextField[]{textAdd3}, textArea);

                    break;

                case 2:

                    table1.setLayoutY(45);
                    table1.setPrefHeight(510);

                    setTable("get_sales",
                            new String[]{"", "Дата", "Сумма", "Продавец", "Состояние",
                                    "ФИО клиента", "Телефон клиента", "Email клиента",
                                    "Телефоны в заказе", "Кол-во телефонов", "Тарифы в заказе", "Кол-во тарифов"},
                            new ObservableList[]{setItemsCombo("get_employees_names"),
                                    FXCollections.observableArrayList("Продано", "Заказ")},
                            new int[]{3, 4});

                    showAndSetValue(null, null, null,
                            null, null, null,
                            null, null, null);
                    setDisableButton(buttonAdd, buttonDelete, null);

                    table1.getColumns().get(2).setEditable(false);
                    table1.getColumns().get(8).setEditable(false);
                    table1.getColumns().get(9).setEditable(false);
                    table1.getColumns().get(10).setEditable(false);
                    table1.getColumns().get(11).setEditable(false);

                    editCellTable("sales");
                    deleteRecord("sales");

                    break;

                case 3:

                    setTable("get_employees",
                            new String[]{"", "ФИО", "Номер телефона", "Email", "Адрес",
                                    "Дата принятия", "Должность", "Зарплата"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd3, textAdd7, textAdd8,
                                    textAdd9, textAdd12, textAdd13, textAdd14},
                            new int[]{1, 2, 3, 4, 5, 6, 7},
                            new String[]{"ФИО", "Номер телефона", "Email",
                                    "Адрес", "Дата принятия", "Должность", "Зарплата"},
                            null, null, null,
                            null, null, null);
                    setDisableButton(buttonAdd, buttonDelete, new TextField[]{textAdd3, textAdd7,
                            textAdd8, textAdd9, textAdd12, textAdd13, textAdd14});

                    editCellTable("employees");
                    deleteRecord("employees");
                    addRecord("employees",
                            new TextField[]{textAdd3, textAdd7, textAdd8, textAdd9, textAdd12, textAdd13, textAdd14},
                            (TextArea) null);

                    break;

                case 4:

                    setTable("get_tariffs",
                            new String[]{"", "Название", "Цена", "Описание", "Услуги в тарифе"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd1, textAdd2}, new int[]{1, 2},
                            new String[]{"Название", "Цена"},
                            null, null, null,
                            textArea, 3, "Описание");
                    setDisableButton(buttonAdd, buttonDelete, new TextField[]{textAdd1, textAdd2});

                    editCellTable("tariffs");
                    deleteRecord("tariffs");
                    addRecord("tariffs", new TextField[]{textAdd1, textAdd2}, textArea);

                    break;

                case 5:

                    setTable("get_offers",
                            new String[]{"", "Название", "Цена", "Дада начала", "Дата окончания", "Описание"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4}, new int[]{1, 2, 3, 4},
                            new String[]{"Название", "Цена", "Дата начала", "Дата окончания"},
                            null, null, null,
                            textArea, 5, "Описание");
                    setDisableButton(buttonAdd, buttonDelete, new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4});

                    editCellTable("offers");
                    deleteRecord("offers");
                    addRecord("offers", new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4}, textArea);

                    break;

                case 6:

                    setTable("get_phones",
                            new String[]{"", "Производитель", "Модель", "ОС", "RAM", "ROM",
                                    "Карта памяти", "Кол-во SIM", "Процессор", "Батарея", "Диагональ",
                                    "Разрешение", "Основная камера", "Вторая камера", "Фронт. камера",
                                    "Цвет", "Цена", "Кол-во единиц"},
                            new ObservableList[]{setItemsCombo("get_manufacturers_names"),
                                    FXCollections.observableArrayList("Поддерживает", "Не поддерживает")},
                            new int[]{1, 6});

                    comboPhoneDetails.getItems().add("- выбор -");
                    setItemsCombo(comboPhoneDetails, "get_pd_model");
                    setItemsCombo(comboAdd1, "get_manufacturers_names");
                    setItemsCombo(comboAdd2, "Поддерживает", "Не поддерживает");

                    showAndSetValue(new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4, textAdd5, textAdd6, textAdd7,
                                    textAdd8, textAdd9, textAdd10, textAdd11, textAdd12, textAdd13, textAdd14, textAdd15},
                            new int[]{2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17},
                            new String[]{"Модель", "ОС", "RAM", "ROM", "Кол-во SIM", "Процессор",
                                    "Батарея", "Диагональ", "Разрешение", "Основная камера",
                                    "Вторая камера", "Фронт. камера", "Цвет", "Цена", "Кол-во единиц"},
                            new ComboBox[]{comboAdd1, comboAdd2, comboPhoneDetails}, new int[]{1, 6, 2},
                            new String[]{"Производитель", "Карта памяти", "Модель"},
                            null, null, null);
                    setDisableButton(buttonAdd, buttonDelete, new TextField[]{textAdd1, textAdd2,
                            textAdd3, textAdd4, textAdd5, textAdd6, textAdd7, textAdd8, textAdd9,
                            textAdd10, textAdd11, textAdd12, textAdd13, textAdd14, textAdd15});

                    editCellTable("phones");
                    deleteRecord("phones");

                    comboPhoneDetails.setOnAction(event1 -> {

                        TextField[] textFields = new TextField[]{textAdd1, textAdd2,
                                textAdd3, textAdd4, textAdd5, textAdd6, textAdd7,
                                textAdd8, textAdd9, textAdd10, textAdd11, textAdd12};

                        switch (comboPhoneDetails.getSelectionModel().getSelectedIndex()) {

                            case 0:

                                for (TextField textField : textFields) {
                                    textField.setDisable(false);
                                }
                                comboAdd2.setDisable(false);
                                break;

                            default:

                                for (TextField textField : textFields) {
                                    textField.setDisable(true);
                                }
                                comboAdd2.setDisable(true);
                                break;
                        }
                    });

                    addRecordPhone(new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4, textAdd5,
                                    textAdd6, textAdd7, textAdd8, textAdd9, textAdd10,
                                    textAdd11, textAdd12, textAdd13, textAdd14, textAdd15},
                            new ComboBox[]{comboAdd1, comboAdd2, comboPhoneDetails});

                    break;

                case 7:

                    setTable("get_manufacturers", new String[]{"", "Название", "Страна"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd8, textAdd13}, new int[]{1, 2},
                            new String[]{"Название", "Страна"},
                            null, null, null,
                            null, null, null);
                    setDisableButton(buttonAdd, buttonDelete, new TextField[]{textAdd8, textAdd13});

                    editCellTable("manufacturers");
                    deleteRecord("manufacturers");
                    addRecord("manufacturers", new TextField[]{textAdd8, textAdd13}, (TextArea) null);

                    break;

            }

        });

    }

    private void setTable(String command, String[] colName,
                          ObservableList<String>[] comboItems, int[] indexColumnCombo) {

        table1.getColumns().clear();
        table1.getItems().clear();

        ArrayList<String[]> records = client.query(command);

        for (int i = 0, j = 0; i < records.get(0).length; i++) {
            TableColumn<String[], String> tableColumn = new TableColumn<>(colName[i]);
            final int col = i;
            tableColumn.setCellValueFactory(
                    (TableColumn.CellDataFeatures<String[], String> param) -> new SimpleStringProperty(param.getValue()[col]));

            boolean combo = false;

            if (comboItems == null && indexColumnCombo == null) {
                tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                tableColumn.setEditable(true);
            } else {
                for (int indexColumn : indexColumnCombo)
                    if (i == indexColumn) {
                        combo = true;
                        break;
                    }
            }
            if (combo) {
                tableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(comboItems[j++]));
            } else {
                tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            table1.getColumns().add(tableColumn);
        }

        ObservableList<String[]> items = FXCollections.observableArrayList(records);

        for (String[] item : items) {
            for (int j = 0; j < item.length; j++) {
                item[j] = item[j].replace("null", "Нет");
            }
        }

        table1.setItems(items);
        table1.getColumns().get(0).setVisible(false);
        table1.setEditable(true);

    }

    private void editCellTable(String tableName) {

        for (int i = 1; i < table1.getColumns().size(); i++) {
            int finalI = i;
            table1.getColumns().get(i).setOnEditCommit(actionEdit -> {
                table1.getFocusModel().getFocusedItem()[finalI] = String.valueOf(actionEdit.getNewValue());

                boolean update = client.query(false, "update_" + tableName
                        + "," + finalI + "," + table1.getFocusModel().getFocusedItem()[finalI]
                        + "," + table1.getFocusModel().getFocusedItem()[0]);

            });

        }
    }

    private void addRecord(String table, TextField[] textFields, TextArea textArea) {
        buttonAdd.setOnAction(event -> {

            String values = "";

            if (textFields != null) {
                for (TextField textField : textFields) {
                    values = values.concat(textField.getText() + ",");
                }
            }

            if (textArea != null) {
                values = values.concat(textArea.getText());
            }

            if (!values.isEmpty()) {
                values = values.substring(0, values.length() - 1);

                boolean stateAdd = client.query(false,
                        "add_" + table + "," + values);

                System.out.println(values);
                System.out.println(stateAdd);

                if (stateAdd) {
                    // TODO: 20.11.2017 ADD TO TABLEVIEW
                    if (textFields != null) {
                        for (TextField textField : textFields) {
                            textField.clear();
                        }
                    }
                    if (textArea != null) {
                        textArea.clear();
                    }
                }
            }

        });
    }

    private void addRecordPhone(TextField[] textFields, ComboBox<String>[] comboBoxes) {
        buttonAdd.setOnAction(event -> {

            if (comboBoxes[2].getSelectionModel().isSelected(0)) {

                String valuesPD = "";

                for (int i = 0; i < textFields.length - 3; i++) {
                    valuesPD = valuesPD.concat(textFields[i].getText() + ",");
                }

                valuesPD = valuesPD.concat(comboBoxes[1].getSelectionModel().getSelectedItem());

                boolean stateAddPD = client.query(false,
                        "add_phone_details," + valuesPD);
                System.out.println("add_phone_details," + valuesPD);
                System.out.println(stateAddPD);

                if (stateAddPD) {

                    String valuesP = comboBoxes[0].getSelectionModel().getSelectedItem()
                            .concat("," + client.query("get_last_pd").get(0)[0]);

                    for (int i = textFields.length - 3; i < textFields.length; i++) {
                        valuesP = valuesP.concat("," + textFields[i].getText());
                    }

                    System.out.println("add_phones_pd," + valuesP);

                    boolean stateAddP = client.query(false,
                            "add_phones_pd," + valuesP);

                    System.out.println(stateAddP);
                }
            } else {

                String values = comboBoxes[0].getSelectionModel().getSelectedItem()
                        .concat("," + comboPhoneDetails.getSelectionModel().getSelectedItem());

                for (int i = textFields.length - 3; i < textFields.length; i++) {
                    values = values.concat("," + textFields[i].getText());
                }

                boolean stateAdd = client.query(false,
                        "add_phones," + values);

                System.out.println(stateAdd);

            }

            // TODO: 20.11.2017 ADD TO TABLEVIEW

        });
    }

    private void deleteRecord(String table) {
        buttonDelete.setOnAction(eventDelete -> {
            if (!table1.getSelectionModel().isEmpty()) {
                boolean stateDelete = client
                        .query(false,
                                "del_" + table + "," + table1.getSelectionModel().getSelectedItem()[0]);
                if (stateDelete) {
                    table1.getItems().remove(table1.getSelectionModel().getSelectedIndex());
                    table1.getSelectionModel().clearSelection();
                    buttonDelete.setDisable(true);
                }
            }
        });
    }

    private void setItemsCombo(ComboBox<String> combo, String sql) {
        ArrayList<String[]> records = client.query(sql);
        ObservableList<String> items = FXCollections.observableArrayList();

        for (String[] record : records) {
            items.add(record[0]);
        }

        combo.getItems().addAll(items);
        combo.getSelectionModel().select(0);
    }

    private void setItemsCombo(ComboBox<String> combo, String... values) {
        combo.getItems().addAll(values);
        combo.getSelectionModel().select(0);
    }

    private ObservableList<String> setItemsCombo(String sql) {
        ArrayList<String[]> records = client.query(sql);
        ObservableList<String> result = FXCollections.observableArrayList();

        for (String[] record : records) {
            result.add(record[0]);
        }

        return result;
    }

    private void showAndSetValue(TextField[] textFields, int[] indexFields, String[] promptFields,
                                 ComboBox[] comboBoxes, int[] indexCombos, String[] promptCombos,
                                 TextArea textArea, Integer indexArea, String promptArea) {

        boolean isTextField = textFields != null && indexFields != null,
                isCombo = comboBoxes != null && indexCombos != null,
                isTextArea = textArea != null && indexArea != null;

        if (isTextField) {
            int i = 0;
            for (TextField textField : textFields) {
                textField.setDisable(false);
                textField.setVisible(true);
                textField.setPromptText(promptFields[i++]);
            }
        }

        if (isCombo) {
            int i = 0;
            for (ComboBox comboBox : comboBoxes) {
                comboBox.setDisable(false);
                comboBox.setVisible(true);
                comboBox.setPromptText(promptCombos[i++]);
            }
        }

        if (isTextArea) {
            textArea.setVisible(true);
            textArea.setPromptText(promptArea);
        }

        table1.setOnMouseClicked(action -> {

            if (!table1.getSelectionModel().isEmpty()) {
                buttonAdd.setDisable(false);
                buttonDelete.setDisable(false);

                if (isTextField) {

                    for (int t = 0; t < textFields.length; t++) {
                        for (int j = 0; j < table1.getColumns().size(); j++) {
                            if (j == indexFields[t]) {
                                textFields[t].setText(table1.getSelectionModel().getSelectedItem()[j]);
                            }
                        }
                    }

                } else {
                    buttonAdd.setDisable(true);
                }

                if (isCombo) {

                    for (int c = 0; c < comboBoxes.length; c++) {
                        for (int j = 0; j < table1.getColumns().size(); j++) {
                            if (j == indexCombos[c]) {
                                comboBoxes[c].setValue(table1.getSelectionModel().getSelectedItem()[j]);
                            }
                        }
                    }

                }

                if (isTextArea) {

                    for (int i = 0; i < table1.getColumns().size(); i++) {
                        if (i == indexArea) {
                            textArea.setText(table1.getSelectionModel().getSelectedItem()[i]);
                        }
                    }

                }

            }

        });
    }

    private void clearAndHide(TextArea textArea) {
        textArea.clear();
        textArea.setVisible(false);
    }

    private void clearAndHide(ComboBox<String>... comboBoxes) {
        for (ComboBox<String> comboBox : comboBoxes) {
            comboBox.getItems().clear();
            comboBox.setDisable(false);
            comboBox.setVisible(false);
        }
    }

    private void clearAndHide(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.clear();
            textField.setDisable(false);
            textField.setVisible(false);
        }
    }

    private void setDisableButton(Button buttonAdd, Button buttonDelete, TextField[] textFields) {

        buttonAdd.setDisable(true);
        buttonDelete.setDisable(true);

        if (textFields != null) {
            for (TextField textField1 : textFields) {
                textField1.setOnKeyReleased(event -> {

                    for (TextField textField2 : textFields) {
                        if (textField2.getText().isEmpty()) {
                            buttonAdd.setDisable(true);
                            break;
                        }
                        buttonAdd.setDisable(false);
                    }

                });
            }
        }

    }

    public Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Controller.client = client;
    }

}