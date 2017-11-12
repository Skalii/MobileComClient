package com.skaliy.mobilecom.client.panes;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Objects;

public class PaneMain extends AnchorPane {

    private String title, content;
    private static int layoutY = 10;

    public PaneMain(String title, String content) {
        this.title = title;
        this.content = content;
        createPaneMain();
    }

    public void createPaneMain() {
        Label labelTitle = new Label(title);
        labelTitle.setFont(new Font("Calibri", 25));
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setPrefWidth(625);
        labelTitle.setLayoutX(10);
        labelTitle.setLayoutY(10);

        Separator separatorTitle = new Separator(Orientation.HORIZONTAL);
        separatorTitle.setPrefWidth(625);
        separatorTitle.setLayoutX(10);
        separatorTitle.setLayoutY(45);

        Label labelContent = setLabelContent();

        getChildren().addAll(labelTitle, separatorTitle, labelContent);

        getStyleClass().add("anchor-pane-content");
        setPrefWidth(645);
        setPrefHeight(labelContent.getLayoutY() + labelContent.getPrefHeight() + 10);
        setLayoutX(10);
        setLayoutY(layoutY);

        layoutY += getPrefHeight() + 10;
    }

    public Label setLabelContent() {
        Label labelContent = new Label();

        String[] lines = content.split(" ");

        int indexWord = 0;
        for (int i = 0; i < content.length(); i++) {
            if (Objects.equals(content.charAt(i), ' '))
                indexWord++;
            for (int j = 90; j < content.length(); j += 90) {
                if (i == j)
                    lines[indexWord] = lines[indexWord].concat("\n");
            }
        }

        content = "";
        for (String line : lines) content = content.concat(line.contains("\n") ? line : line.concat(" "));

        int countLines = 1;
        for (int i = 0; i < content.length(); i++) {
            if (Objects.equals(String.valueOf(content.charAt(i)), "\n")) {
                countLines++;
            }
        }

        labelContent.setText(content);
        labelContent.setFont(new Font("Calibri", 14));
        labelContent.setPrefWidth(625);
        labelContent.setPrefHeight(20 * countLines);
        labelContent.setLayoutX(10);
        labelContent.setLayoutY(58);
        labelContent.setWrapText(true);

        return labelContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static int getHeightPaneMain() {
        return layoutY;
    }

}