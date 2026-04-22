package com.zukuk.controller;

import com.zukuk.MainApp;
import com.zukuk.service.SaveService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DefaiteController {

    @FXML private ImageView imageExplosion;

    @FXML
    public void initialize() {
        try {
            Image img = new Image(
                getClass().getResourceAsStream("/com/zukuk/images/explosion.png")
            );
            imageExplosion.setImage(img);
        } catch (Exception e) {}

        SaveService.effacerSauvegarde();
    }

    @FXML
    private void handleReessayer() {
        MainApp.loadScene("menu.fxml");
    }

    @FXML
    private void handleQuitter() {
        javafx.application.Platform.exit();
    }
}
