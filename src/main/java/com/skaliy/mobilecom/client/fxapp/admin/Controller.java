package com.skaliy.mobilecom.client.fxapp.admin;

import com.skaliy.mobilecom.client.client.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.time.LocalDate;
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
    private TableView<String[]> table1, table2, table3;

    private static Client client;

    public void initialize() {

        comboSetTable.getItems().addAll("- выбор таблицы -", "Новости",
                "Продажи", "Сотрудники", "Тарифы", "Услуги", "Телефоны", "Производители");

        comboSetTable.setOnAction(event -> {

            clearAndShow(table1);

            clearAndHide(table2, table3);
            clearAndHide(textArea);
            clearAndHide(comboAdd1, comboAdd2);
            clearAndHide(textAdd1, textAdd2, textAdd3, textAdd4, textAdd5,
                    textAdd6, textAdd7, textAdd8, textAdd9, textAdd10,
                    textAdd11, textAdd12, textAdd13, textAdd14, textAdd15);

            comboSetTable.getSelectionModel().clearSelection(0);

            switch (comboSetTable.getSelectionModel().getSelectedIndex()) {

                case 0:
                    clearAndHide(table1);
                    break;

                case 1:

                    setTable(table1, "get_news",
                            new String[]{"", "Название", "Текст"},
                            null, null);

                    showAndSetValue(table1, new TextField[]{textAdd3}, new int[]{1},
                            null, null, textArea, 2, null, null);

                    break;

                case 2:

                    table1.setPrefWidth(400);
                    table2.setPrefHeight(180);
                    clearAndShow(table2);
                    clearAndShow(table3);

                    break;

                case 3:

                    setTable(table1, "get_employees",
                            new String[]{"", "ФИО", "Номер телефона", "Email", "Адрес",
                                    "Дата принятия", "Должность", "Зарплата"},
                            null, null);

                    showAndSetValue(table1,
                            new TextField[]{textAdd3, textAdd7, textAdd8, textAdd9, textAdd12, textAdd13, textAdd14},
                            new int[]{1, 2, 3, 4, 5, 6, 7},
                            null, null, null, null, null, null);

                    break;

                case 4:

                    table1.setPrefWidth(400);
                    table2.setPrefHeight(370);
                    clearAndShow(table2);

                    break;

                case 5:

                    setTable(table1, "get_offers",
                            new String[]{"", "Название", "Цена", "Дада начала", "Дата окончания", "Описание"},
                            null, null);

                    showAndSetValue(table1,
                            new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4}, new int[]{1, 2, 3, 4},
                            null, null, textArea, 5, null, null);

                    break;

                case 6:

                    setTable(table1, "get_phones",
                            new String[]{"", "Производитель", "Модель", "ОС", "RAM", "ROM",
                                    "Карта памяти", "Кол-во SIM", "Процессор", "Батарея", "Диагональ",
                                    "Разрешение", "Основная камера", "Вторая камера", "Фронт. камера",
                                    "Цвет", "Цена", "Кол-во единиц"},
                            new ObservableList[]{setItemsCombo("get_m_names"),
                                    FXCollections.observableArrayList("Поддерживает", "Не поддерживает")},
                            new int[]{1, 6});

                    setItemsCombo(comboAdd1, "get_m_names");
                    setItemsCombo(comboAdd2, "Поддерживается", "Не поддерживается");

                    showAndSetValue(table1,
                            new TextField[]{textAdd1, textAdd2, textAdd3, textAdd4, textAdd5, textAdd6, textAdd7,
                                    textAdd8, textAdd9, textAdd10, textAdd11, textAdd12, textAdd13, textAdd14, textAdd15},
                            new int[]{2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17},
                            new ComboBox[]{comboAdd1, comboAdd2}, new int[]{1, 6},
                            null, null, null, null);

                    break;

                case 7:

                    setTable(table1, "get_manufacturers", new String[]{"", "Название", "Страна"},
                            null, null);

                    showAndSetValue(table1,
                            new TextField[]{textAdd8, textAdd13}, new int[]{1, 2},
                            null, null, null, null, null, null);

                    break;

            }

        });

    }

    private void setTable(TableView<String[]> table, String sql, String[] colName,
                          ObservableList<String>[] comboItems, int[] indexColumnCombo) {

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
            table.getColumns().add(tableColumn);
        }

        ObservableList<String[]> records = FXCollections.observableArrayList(client.query(sql));

        /*for (String[] record : records) {
            for (int j = 0; j < record.length; j++) {
                record[j] = record[j].replace("null", "Нет");
            }
        }*/

        table.setItems(records);
        table.getColumns().get(0).setVisible(false);
        table.setEditable(true);

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

    private void showAndSetValue(TableView<String[]> table,
                                 TextField[] texts, int[] indexTexts,
                                 ComboBox[] combos, int[] indexCombos,
                                 TextArea textArea, Integer indexTextArea,
                                 DatePicker date, Integer indexDate) {

        boolean isTextField = texts != null && indexTexts != null,
                isCombo = combos != null && indexCombos != null,
                isTextArea = textArea != null && indexTextArea != null,
                isDate = date != null && indexDate != null;

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

        if (isDate) {
            date.setVisible(true);
        }

        table.setOnMouseClicked(action -> {

            if (!table.getSelectionModel().isEmpty()) {

                if (isTextField) {

                    for (int t = 0; t < texts.length; t++) {
                        for (int j = 0; j < table.getColumns().size(); j++) {
                            if (j == indexTexts[t]) {
                                texts[t].setText(table.getSelectionModel().getSelectedItem()[j]);
                            }
                        }
                    }

                }

                if (isCombo) {

                    for (int c = 0; c < combos.length; c++) {
                        for (int j = 0; j < table.getColumns().size(); j++) {
                            if (j == indexCombos[c]) {
                                combos[c].setValue(table.getSelectionModel().getSelectedItem()[j]);
                            }
                        }
                    }

                }

                if (isTextArea) {

                    for (int i = 0; i < table.getColumns().size(); i++) {
                        if (i == indexTextArea) {
                            textArea.setText(table.getSelectionModel().getSelectedItem()[i]);
                        }
                    }

                }

                if (isDate) {

                    for (int i = 0; i < table.getColumns().size(); i++) {
                        if (i == indexDate) {
                            date.setValue(LocalDate.parse(table.getSelectionModel().getSelectedItem()[i]));
                        }
                    }

                }

            }
        });
    }

    private void clearAndShow(TableView<String[]> table) {
        table.getItems().clear();
        table.getColumns().clear();
        table.setDisable(false);
        table.setVisible(true);
        table.setPrefWidth(680);
    }

    private void clearAndHide(TableView<String[]>... table) {
        for (TableView tableView : table) {
            tableView.setVisible(false);
            tableView.setDisable(true);
            tableView.getItems().clear();
            tableView.getColumns().clear();
        }
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