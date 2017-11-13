package com.skaliy.mobilecom.client.panes;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PaneParent extends AnchorPane {

    public final static int
            PANE_MAIN = 1,
            PANE_TARIFF = 2,
            PANE_OFFER = 3;

    private String title, general, availableOffers;
    private double price;
    private Date dateStart, dateEnd;
    private int THIS_PANE;

    private static int layoutY = 10;

    public PaneParent(int PANE, String[] values) {
        THIS_PANE = PANE;

        if (THIS_PANE == PANE_MAIN) {
            title = values[1];
            general = values[2];
        } else if ((THIS_PANE == PANE_TARIFF) || (THIS_PANE == PANE_OFFER)) {
            title = values[1];
            price = Double.parseDouble(values[2].substring(1));

            if (THIS_PANE != PANE_OFFER) {
                general = values[3];
                availableOffers = values[4];
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateStart = simpleDateFormat.parse(values[3]);
                    dateEnd = simpleDateFormat.parse(values[4]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                general = values[5];
            }
        }

        createPane();
    }

    private void createPane() {
        int prefWidth = (THIS_PANE == PANE_MAIN) ? 625
                : ((THIS_PANE == PANE_TARIFF) || THIS_PANE == PANE_OFFER) ? 410 : 0;

        Label labelTitle = new Label(title);
        labelTitle.setFont(new Font("Calibri", 25));
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setLayoutX(10);
        labelTitle.setLayoutY(10);
        labelTitle.setPrefWidth(prefWidth);

        Separator separatorTitle = new Separator(Orientation.HORIZONTAL);
        separatorTitle.setPrefWidth(prefWidth);
        separatorTitle.setLayoutX(10);
        separatorTitle.setLayoutY(45);

        Label labelGeneral = setLabelGeneral(prefWidth);

        getChildren().addAll(labelTitle, separatorTitle, labelGeneral);

        double prefHeight = 0;

        if (THIS_PANE == PANE_MAIN) {
            prefHeight = labelGeneral.getLayoutY() + labelGeneral.getPrefHeight() + 10;

        } else if ((THIS_PANE == PANE_TARIFF) || THIS_PANE == PANE_OFFER) {
            Separator separatorGeneral = new Separator(Orientation.HORIZONTAL);
            separatorGeneral.setPrefWidth(prefWidth);
            separatorGeneral.setLayoutX(10);
            separatorGeneral.setLayoutY(labelGeneral.getLayoutY() + labelGeneral.getPrefHeight() + 10);

            Label labelPrice = new Label("Стоимость: " + price + " грн/м");
            labelPrice.setFont(new Font("Calibri", 14));
            labelPrice.setAlignment(Pos.CENTER);
            labelPrice.setPrefWidth(prefWidth);
            labelPrice.setLayoutX(10);
            labelPrice.setLayoutY(separatorGeneral.getLayoutY() + 13);

            getChildren().addAll(separatorGeneral, labelPrice);

            if (THIS_PANE == PANE_TARIFF) {

                if (availableOffers.length() > 2) {

                    Separator separatorPrice = new Separator(Orientation.HORIZONTAL);
                    separatorPrice.setPrefWidth(prefWidth);
                    separatorPrice.setLayoutX(10);
                    separatorPrice.setLayoutY(labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 20);

                    Label labelAvailable1Offers = new Label(
                            "Доступные услуги:\n\n"
                                    + availableOffers);
                    labelAvailable1Offers.setFont(new Font("Calibri", 14));
                    labelAvailable1Offers.setAlignment(Pos.CENTER);
                    labelAvailable1Offers.setPrefWidth(prefWidth);
                    labelAvailable1Offers.setPrefHeight(20 * getCountLines(labelAvailable1Offers.getText()));
                    labelAvailable1Offers.setLayoutX(10);
                    labelAvailable1Offers.setLayoutY(separatorPrice.getLayoutY() + 13);

                    getChildren().addAll(separatorPrice, labelAvailable1Offers);

                    prefHeight = labelAvailable1Offers.getLayoutY() + labelAvailable1Offers.getPrefHeight() + 20;
                } else {
                    prefHeight = labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 30;
                }

            } else {
                Separator separatorPrice = new Separator(Orientation.HORIZONTAL);
                separatorPrice.setPrefWidth(prefWidth);
                separatorPrice.setLayoutX(10);
                separatorPrice.setLayoutY(labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 30);

                Label labelDate = new Label(dateStart.toString() + " - " + dateEnd.toString());
                labelDate.setFont(new Font("Calibri", 14));
                labelDate.setAlignment(Pos.CENTER);
                labelDate.setPrefWidth(prefWidth);
                labelDate.setLayoutX(10);
                labelDate.setLayoutY(separatorPrice.getLayoutY() + 13);

                getChildren().addAll(separatorPrice, labelDate);

                prefHeight = labelDate.getLayoutY() + labelDate.getPrefHeight() + 30;
            }
        }

        getStyleClass().add("anchor-pane-content");
        setPrefWidth(prefWidth + 20);
        setPrefHeight(prefHeight);
        setLayoutX(10);
        setLayoutY(layoutY);

        layoutY += getPrefHeight() + 10;
    }

    private Label setLabelGeneral(int prefWidth) {
        Label labelContent = new Label();

        labelContent.setText(general);
        labelContent.setFont(new Font("Calibri", 14));
        labelContent.setPrefWidth(prefWidth);
        labelContent.setPrefHeight(20 * getCountLines(general));
        labelContent.setLayoutX(10);
        labelContent.setLayoutY(58);
        labelContent.setWrapText(true);

        return labelContent;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

}