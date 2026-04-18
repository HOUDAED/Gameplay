package com.zukuk.controller;

import com.zukuk.MainApp;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AccueilController {

    @FXML private AnchorPane rootPane;
    @FXML private VBox contenuVBox;

    @FXML
    public void initialize() {
        rootPane.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), rootPane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void handleCommencerAventure() {
        FadeTransition fade = new FadeTransition(Duration.millis(700), rootPane);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> MainApp.loadScene("menu.fxml"));
        fade.play();
    }
}