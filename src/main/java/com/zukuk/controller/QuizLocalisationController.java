package com.zukuk.controller;

import javafx.fxml.FXML;

public class QuizLocalisationController extends QuizController {

    @FXML
    public void initialize() {
        sceneSuivante = "dialogue_inter2.fxml";
        super.initialize();
    }
}