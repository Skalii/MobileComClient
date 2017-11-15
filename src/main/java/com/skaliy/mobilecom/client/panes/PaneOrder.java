package com.skaliy.mobilecom.client.panes;

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

    private static double layoutYNextPane = 10;
    private static int records = 0;

    private PaneRecord paneRecord;
    private String unitOrder;
    private double price;
    private int units, indexThisCancel, thisRecord;

    public PaneOrder(PaneRecord paneRecord) {

        this.paneRecord = paneRecord;

        if (paneRecord.getTHIS_PANE() == PANE_PHONE) {

            unitOrder = paneRecord.getManufacturer() + " " + paneRecord.getModel();
            price = paneRecord.getPrice();

            units = 1;
            thisRecord = records++;

        } else if (paneRecord.getTHIS_PANE() == PANE_TARIFF) {

            unitOrder = paneRecord.getTitle();
            price = paneRecord.getPrice();

            units = 1;
            thisRecord = records++;
        }
        createPane();
    }

    private void createPane() {

        Label labelTitleOrder = newLabel("Товар в корзине:\n" + unitOrder,
                20, 1, 10);

        Separator separatorTitleOrder = newSeparator(
                labelTitleOrder.getLayoutY() + labelTitleOrder.getPrefHeight() + 10);

        Label labelPrice = newLabel("Сумма: " + (price * units),
                14, 1, separatorTitleOrder.getLayoutY() + 13);
        Label labelUnits = newLabel("Количество: " + units,
                14, 1, labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 10);

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
        setLayoutY(layoutYNextPane);

        layoutYNextPane += getPrefHeight() + 10;
    }

    private Label newLabel(String text, int textSize, int alignment, double layoutY) {
        Label label = new Label();

        label.setText(text);
        label.setFont(new Font("Calibri", textSize));
        label.setPrefSize(625, Math.round(textSize * 1.5) * getCountLines(text));
        label.setLayoutX(10);
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

    public double getLayoutYNextPane() {
        return layoutYNextPane;
    }

    public void setLayoutYNextPane(double layoutYNextPane) {
        PaneOrder.layoutYNextPane = layoutYNextPane;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public PaneRecord getPaneRecord() {
        return paneRecord;
    }

    public void setPaneRecord(PaneRecord paneRecord) {
        this.paneRecord = paneRecord;
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

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        PaneOrder.records = records;
    }

    public int getIndexThisCancel() {
        return indexThisCancel;
    }

    public void setIndexThisCancel(int indexThisCancel) {
        this.indexThisCancel = indexThisCancel;
    }

}