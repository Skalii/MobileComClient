package com.skaliy.mobilecom.client.panes;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

import static com.skaliy.mobilecom.client.panes.PaneRecord.*;

public class PaneOrder extends AnchorPane {

    private static double layoutY = 10;
    private static int records = 0;

    private PaneRecord paneRecord;
    private String unitOrder;
    private double price;
    private int units = 0, indexThisCancel, thisRecord;
    private boolean select = false;

    public PaneOrder(int record, ObservableList<PaneRecord> paneRecords) {

        for (int i = 0; i < paneRecords.size(); i++) {
            if (i == record) {
                if (paneRecords.get(i).getTHIS_PANE() == PANE_PHONE) {
                    paneRecord = paneRecords.get(i);

                    unitOrder =
                            paneRecord.getManufacturer() + " " + paneRecord.getModel();
                    price = paneRecord.getPrice();
                    units++;
                    thisRecord = records++;
                    select = true;

                } else if (paneRecords.get(i).getTHIS_PANE() == PANE_TARIFF) {
                    paneRecord = paneRecords.get(i);

                    unitOrder = paneRecord.getTitle();
                    price = paneRecord.getPrice();
                    units++;
                    thisRecord = records++;
                    select = true;
                }
            }
        }
        createPane();
    }

    private void createPane() {

        Label labelTitleOrder = newLabel("Товар в корзине:\n" + unitOrder,
                20, 1, 10, 10);

        Separator separatorTitleOrder = newSeparator(
                labelTitleOrder.getLayoutY() + labelTitleOrder.getPrefHeight() + 10);

        Label labelPrice = newLabel("Сумма: " + (price * units),
                14, 1, 10, separatorTitleOrder.getLayoutY() + 13);
        Label labelUnits = newLabel("Количество: " + units,
                14, 1, 10,
                labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 10);

        ImageView imageCancel = new ImageView();
        imageCancel.getStyleClass().add("image-cancel");
        imageCancel.setFitWidth(30);
        imageCancel.setFitHeight(30);
        imageCancel.setLayoutX(585);
        imageCancel.setLayoutY(separatorTitleOrder.getLayoutY() + 13);

        getChildren().addAll(labelTitleOrder, separatorTitleOrder, labelPrice, labelUnits, imageCancel);

        indexThisCancel = getChildren().size() - 1;

        getStyleClass().add("anchor-pane-content");
        setPrefWidth(645);
        setPrefHeight(labelUnits.getLayoutY() + labelUnits.getPrefHeight() + 10);
        setLayoutX(10);
        setLayoutY(layoutY);

        layoutY += getPrefHeight() + 10;
    }

    private Label newLabel(String text, int textSize, int alignment, double layoutX, double layoutY) {
        Label label = new Label();

        label.setText(text);
        label.setFont(new Font("Calibri", textSize));
        label.setPrefSize(625, Math.round(textSize * 1.5) * getCountLines(text));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);

        label.setAlignment(
                alignment == 2 ? Pos.CENTER
                        : alignment == 3 ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        label.setTextAlignment(
                alignment == 2 ? TextAlignment.CENTER
                        : alignment == 3 ? TextAlignment.RIGHT : TextAlignment.LEFT);

        return label;
    }

    private Separator newSeparator(double layoutY) {
        Separator separator = new Separator(Orientation.HORIZONTAL);

        separator.setPrefWidth(625);
        separator.setLayoutX(10);
        separator.setLayoutY(layoutY);

        return separator;
    }

    private int getCountLines(String search) {
        int countLines = 1;
        for (int i = 0; i < search.length(); i++) {
            if (Objects.equals(String.valueOf(search.charAt(i)), "\n")) {
                countLines++;
            }
        }
        return countLines;
    }

    public static double getPanesHeight() {
        //int layout = layoutY;
//        layoutY = 10;
        return layoutY;
    }

    public static void setPanesHeight(double height) {
        layoutY = height;
    }

    public String getUnitOrder() {
        return unitOrder;
    }

    public void setUnitOrder(String unitOrder) {
        this.unitOrder = unitOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getThisRecord() {
        return thisRecord;
    }

    public void setThisRecord(int thisRecord) {
        this.thisRecord = thisRecord;
    }

    public static int getRecords() {
        return records;
    }

    public static void setRecords(int records) {
        PaneOrder.records = records;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getIndexThisCancel() {
        return indexThisCancel;
    }

    public void setIndexThisCancel(int indexThisCancel) {
        this.indexThisCancel = indexThisCancel;
    }
}