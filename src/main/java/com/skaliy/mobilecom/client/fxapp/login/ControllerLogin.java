package com.skaliy.mobilecom.client.fxapp.login;

import com.skaliy.mobilecom.client.connection.Client;
import com.skaliy.mobilecom.client.fxapp.admin.MainAdmin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class ControllerLogin {

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Button buttonLogin, buttonClose;

    @FXML
    private Label labelError;

    private static Client client;

    private boolean isEmail = false, isPassword = false;

    public void initialize() {

        buttonClose.setOnAction(event -> {
            MainLogin.getStage().close();
        });

        buttonLogin.setOnAction(event -> {

            isEmail =
                    Objects.equals(textEmail.getText(),
                            client.query("get_email_employee_p-" + textEmail.getText()).get(0)[0]);

            if (isEmail) {
                labelError.setVisible(false);

                isPassword = Objects.equals(textPassword.getText(),
                        client.query("get_pass_employee_c-Е = '"
                                + textEmail.getText() + "';П = '" + textPassword.getText() + "'").get(0)[0]);

                if (isPassword) {
                    labelError.setVisible(false);

                    MainLogin.getStage().close();

                    MainAdmin mainAdmin = new MainAdmin();
                    try {
                        mainAdmin.start(new Stage());
                        mainAdmin.setClient(client);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    labelError.setVisible(true);
                    labelError.setText("Неверный Email или пароль");
                }

            } else {
                labelError.setVisible(true);
                labelError.setText("Неверный Email или пароль");
            }

        });

        textEmail.setOnAction(buttonLogin.getOnAction());
        textPassword.setOnAction(buttonLogin.getOnAction());

    }

    static void setClient(Client client) {
        ControllerLogin.client = client;
    }

}
