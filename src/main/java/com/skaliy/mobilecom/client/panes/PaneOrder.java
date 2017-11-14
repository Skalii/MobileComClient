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

    private static int layoutY = 10;

    private PaneRecord paneRecord;
    private String unitOrder;
    private double price;
    private int units = 0;

    public PaneOrder(int record, ObservableList<PaneRecord> paneRecords) {

        for (int i = 0; i < paneRecords.size(); i++) {
            if (i == record) {
                if (paneRecords.get(i).getTHIS_PANE() == PANE_PHONE) {
                    paneRecord = paneRecords.get(i);

                    unitOrder =
                            paneRecord.getManufacturer() + " " + paneRecord.getModel();
                    price = paneRecord.getPrice();
                    units++;

                } else if (paneRecords.get(i).getTHIS_PANE() == PANE_TARIFF) {
                    paneRecord = paneRecords.get(i);

                    unitOrder = paneRecord.getTitle();
                    price = paneRecord.getPrice();
                    units++;
                }
            }
        }
        createPane();
    }

    private void createPane() {

        Label labelTitleOrder = newLabel("Товар в корзине: " + unitOrder,
                20, 1, 10, 10);

        Separator separatorTitleOrder = newSeparator(
                labelTitleOrder.getLayoutY() + labelTitleOrder.getPrefHeight() + 10);

        Label labelPrice = newLabel("Сумма: " + (price * units),
                14, 1, 10, separatorTitleOrder.getLayoutY() + 13);
        Label labelUnits = newLabel("Количество: " + units,
                14, 3,
                labelPrice.getLayoutX() + labelPrice.getPrefWidth(),
                separatorTitleOrder.getLayoutY() + 13);

        ImageView imageCancel = new ImageView();
        imageCancel.getStyleClass().add("image-cancel");
        imageCancel.setFitWidth(20);
        imageCancel.setFitHeight(20);
        imageCancel.setLayoutX(625);
        imageCancel.setLayoutY(separatorTitleOrder.getLayoutY() + 13);

        getChildren().addAll(labelTitleOrder, separatorTitleOrder, labelPrice, labelUnits, imageCancel);

        getStyleClass().add("anchor-pane-content");
        setPrefWidth(625);
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

    public static int getAndReplaceHeight() {
        int layout = layoutY;
        layoutY = 10;
        return layout;
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

}