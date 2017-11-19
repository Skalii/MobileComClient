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
    private ComboBox<String> comboSetTable, comboAdd1, comboAdd2;

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
            clearAndHide(comboAdd1, comboAdd2);
            clearAndHide(textAdd1, textAdd2, textAdd3, textAdd4, textAdd5,
                    textAdd6, textAdd7, textAdd8, textAdd9, textAdd10,
                    textAdd11, textAdd12, textAdd13, textAdd14, textAdd15);

            switch (comboSetTable.getSelectionModel().getSelectedIndex()) {

                case 0:
                    table1.setDisable(true);
                    table1.getColumns().clear();
                    table1.getItems().clear();
                    break;

                case 1:

                    setTable("get_news",
                            new String[]{"", "Название", "Текст"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd3}, new int[]{1},
                            null, null, textArea, 2);

                    editCellTable("news");
                    deleteRecord("news");

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
                            null, null, null, null);

                    editCellTable("employees");
                    deleteRecord("employees");

                    break;

                case 4:

                    setTable("get_tariffs",
                            new String[]{"", "Название", "Цена", "Описание", "Услуги в тарифе"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd1, textAdd2}, new int[]{1, 2},
                            null, null, textArea, 3);

                    editCellTable("tariffs");
                    deleteRecord("tariffs");

                    break;

                case 5:

                    setTable("get_offers",
                            new String[]{"", "Название", "Цена", "Дада начала", "Дата окончания", "Описание"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4}, new int[]{1, 2, 3, 4},
                            null, null, textArea, 5);

                    editCellTable("offers");
                    deleteRecord("offers");

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

                    setItemsCombo(comboAdd1, "get_manufacturers_names");
                    setItemsCombo(comboAdd2, "Поддерживается", "Не поддерживается");

                    showAndSetValue(new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4, textAdd5, textAdd6, textAdd7,
                                    textAdd8, textAdd9, textAdd10, textAdd11, textAdd12, textAdd13, textAdd14, textAdd15},
                            new int[]{2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17},
                            new ComboBox[]{comboAdd1, comboAdd2}, new int[]{1, 6},
                            null, null);

                    editCellTable("phones");
                    deleteRecord("phones");

                    break;

                case 7:

                    setTable("get_manufacturers", new String[]{"", "Название", "Страна"},
                            null, null);

                    showAndSetValue(new TextField[]{textAdd8, textAdd13}, new int[]{1, 2},
                            null, null, null, null);

                    editCellTable("manufacturers");
                    deleteRecord("manufacturers");

                    break;

            }

        });

    }

    private void setTable(String sql, String[] colName,
                          ObservableList<String>[] comboItems, int[] indexColumnCombo) {

        table1.getColumns().clear();
        table1.getItems().clear();

        for (int i = 0, j = 0; i < client.query(sql).get(0).length; i++) {
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

        ObservableList<String[]> records = FXCollections.observableArrayList(client.query(sql));

        for (String[] record : records) {
            for (int j = 0; j < record.length; j++) {
                record[j] = record[j].replace("null", "Нет");
            }
        }

        table1.setItems(records);
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

    private void deleteRecord(String table) {
        buttonDelete.setOnAction(eventDelete -> {
            if (!table1.getSelectionModel().isEmpty()) {
                boolean stateDelete = client
                        .query(false,
                                "del_" + table + "," + table1.getSelectionModel().getSelectedItem()[0]);
                if (stateDelete) {
                    table1.getItems().remove(table1.getSelectionModel().getSelectedIndex());
                    table1.getSelectionModel().clearSelection();
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

    private void showAndSetValue(TextField[] texts, int[] indexTexts,
                                 ComboBox[] combos, int[] indexCombos,
                                 TextArea textArea, Integer indexTextArea) {

        boolean isTextField = texts != null && indexTexts != null,
                isCombo = combos != null && indexCombos != null,
                isTextArea = textArea != null && indexTextArea != null;

        if (isTextField) {
            for (TextField text : texts) {
                text.setVisible(true);
            }
        }

        if (isCombo) {
            for (ComboBox aCombos : combos) {
                aCombos.setVisible(true);
            }
        }

        if (isTextArea) {
            textArea.setVisible(true);
        }

        table1.setOnMouseClicked(action -> {

            if (!table1.getSelectionModel().isEmpty()) {

                if (isTextField) {

                    for (int t = 0; t < texts.length; t++) {
                        for (int j = 0; j < table1.getColumns().size(); j++) {
                            if (j == indexTexts[t]) {
                                texts[t].setText(table1.getSelectionModel().getSelectedItem()[j]);
                            }
                        }
                    }

                }

                if (isCombo) {

                    for (int c = 0; c < combos.length; c++) {
                        for (int j = 0; j < table1.getColumns().size(); j++) {
                            if (j == indexCombos[c]) {
                                combos[c].setValue(table1.getSelectionModel().getSelectedItem()[j]);
                            }
                        }
                    }

                }

                if (isTextArea) {

                    for (int i = 0; i < table1.getColumns().size(); i++) {
                        if (i == indexTextArea) {
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
        for (ComboBox<String> comboBoxe : comboBoxes) {
            comboBoxe.getItems().clear();
            comboBoxe.setVisible(false);
        }
    }

    private void clearAndHide(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.clear();
            textField.setVisible(false);
        }
    }

    public Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Controller.client = client;
    }

}