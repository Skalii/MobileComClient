package com.skaliy.mobilecom.fxapp;

import com.skaliy.dbc.dbms.PostgreSQL;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class Controller {

    @FXML
    private MenuItem menuCreateBackup, menuRestoreBackup, menuHideTray, menuExit,
            menuLogin, menuProfile, menuAbout;

    @FXML
    private SeparatorMenuItem separatorMenuBackup;

    private PostgreSQL db;

    public void initialize() {
        /*      connection to the local server

        db = new PostgreSQL("localhost:5432/mobile_com", "postgres", "masterkey");*/

        db = new PostgreSQL(
                "ec2-79-125-13-42.eu-west-1.compute.amazonaws.com:5432/d81947dprpqjnd?sslmode=require",
                "nesmvtsalawsxv",
                "a1c0a6be9d1e3a3265e71b393cce3253858e7108282575d89e6ebe3bf2e25276");

    }

}
