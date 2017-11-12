package com.skaliy.mobilecom.client.panes;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Objects;

public class PaneTariff extends AnchorPane {

    private String title, description;
    private double price;
    private static int layoutY = 10;

    public PaneTariff(String title, double price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
        createPaneTariff();
    }

    private void createPaneTariff() {
        Label labelTitle = new Label(title);
        labelTitle.setFont(new Font("Calibri", 25));
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setPrefWidth(410);
        labelTitle.setLayoutX(10);
        labelTitle.setLayoutY(10);

        Separator separatorTitle = new Separator(Orientation.HORIZONTAL);
        separatorTitle.setPrefWidth(410);
        separatorTitle.setLayoutX(10);
        separatorTitle.setLayoutY(45);

        Label labelDescription = setLabelDescription();

        Separator separatorDescription = new Separator(Orientation.HORIZONTAL);
        separatorDescription.setPrefWidth(410);
        separatorDescription.setLayoutX(10);
        separatorDescription.setLayoutY(labelDescription.getLayoutY() + labelDescription.getPrefHeight() + 10);

        Label labelPrice = new Label("Стоимость: " + price + " грн/м");
        labelPrice.setFont(new Font("Calibri", 14));
        labelPrice.setAlignment(Pos.CENTER);
        labelPrice.setPrefWidth(410);
        labelPrice.setLayoutX(10);
        labelPrice.setLayoutY(separatorDescription.getLayoutY() + 13);

        getChildren().addAll(labelTitle, separatorTitle, labelDescription, separatorDescription, labelPrice);

        getStyleClass().add("anchor-pane-content");
        setPrefWidth(430);
        setPrefHeight(labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 20);
        setLayoutX(10);
        setLayoutY(layoutY);

        layoutY += getPrefHeight() + 10;
    }

    private Label setLabelDescription() {
        Label labelContent = new Label();

        String[] lines = description.split(" ");

        int indexWord = 0;
        for (int i = 0; i < description.length(); i++) {
            if (Objects.equals(description.charAt(i), ' '))
                indexWord++;
            for (int j = 55; j < description.length(); j += 55) {
                if (i == j)
                    lines[indexWord] = lines[indexWord].concat("\n");
            }
        }

        description = "";
        for (String line : lines) description = description.concat(line.contains("\n") ? line : line.concat(" "));

        int countLines = 1;
        for (int i = 0; i < description.length(); i++) {
            if (Objects.equals(String.valueOf(description.charAt(i)), "\n")) {
                countLines++;
            }
        }

        labelContent.setText(description);
        labelContent.setFont(new Font("Calibri", 14));
        labelContent.setPrefWidth(410);
        labelContent.setPrefHeight(20 * countLines);
        labelContent.setLayoutX(10);
        labelContent.setLayoutY(58);
        labelContent.setWrapText(true);

        return labelContent;
    }

    public static int getHeightPaneTariff() {
        return layoutY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}