package com.skaliy.mobilecom.client.fxapp.admin;

import com.skaliy.mobilecom.client.connection.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainAdmin extends Application {

    private static SystemTray systemTray;
    private static TrayIcon trayIcon;
    private MenuItem menuOpen, menuExit;
    private PopupMenu popupMenu;
    private static Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
        primaryStage.setTitle("Администрирование");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            systemTray.remove(trayIcon);
            primaryStage.close();
        });

        stage = primaryStage;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Platform.setImplicitExit(false);
                try {
                    systemTray = SystemTray.getSystemTray();

                    trayIcon = new TrayIcon(ImageIO.read(new File("src/main/resources/images/icons/icon_tray_admin.png")));
                    trayIcon.addActionListener(e -> {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                primaryStage.show();
                                primaryStage.setIconified(false);
                                primaryStage.toFront();
                            }
                        });
                    });

                    menuOpen = new MenuItem("Открыть");
                    menuOpen.addActionListener(e -> {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                primaryStage.show();
                                primaryStage.setIconified(false);
                                primaryStage.toFront();
                            }
                        });
                    });

                    menuExit = new MenuItem("Закрыть");
                    menuExit.addActionListener(e -> primaryStage.close());

                    popupMenu = new PopupMenu();
                    popupMenu.add(menuOpen);
                    popupMenu.add(menuExit);

                    trayIcon.setPopupMenu(popupMenu);
                    systemTray.add(trayIcon);
                } catch (IOException | AWTException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setClient(Client client) {
        ControllerAdmin.setClient(client);
    }

    static Stage getStage() {
        return stage;
    }

    static SystemTray getSystemTray() {
        return systemTray;
    }

    static TrayIcon getTrayIcon() {
        return trayIcon;
    }

}
