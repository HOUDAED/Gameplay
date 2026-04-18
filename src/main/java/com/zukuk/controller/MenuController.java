package com.zukuk.controller;

import com.zukuk.MainApp;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private VBox rootPane;
    @FXML private javafx.scene.control.Button btnQuitter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootPane.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), rootPane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void handleNouvelle() {
        FadeTransition fade = new FadeTransition(Duration.millis(600), rootPane);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> MainApp.loadScene("dialogue.fxml"));
        fade.play();
    }

    @FXML
    private void handleReprendre() {
    }

    @FXML
    private void handleQuitter() {
        Stage stage = (Stage) btnQuitter.getScene().getWindow();
        stage.close();
    }
}