package com.zukuk.controller;

import com.zukuk.MainApp;
import com.zukuk.model.QuizQuestion;
import com.zukuk.service.QuizApiService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Quiz3Controller {

    @FXML private AnchorPane rootPane;
    @FXML private Label      labelScore;
    @FXML private Label      labelQuestion;
    @FXML private Label      labelFeedback;
    @FXML private Label      labelTentatives;
    @FXML private Button     btn0, btn1, btn2, btn3;
    @FXML private Button     btnSuivant;
    @FXML private GridPane   gridReponses;

    private final QuizApiService api            = new QuizApiService();
    private QuizQuestion         questionActuelle;
    private int                  score          = 0;
    private int                  tentatives     = 0;
    private static final int     MAX_TENTATIVES = 10;
    private Button[]             boutons;

    @FXML
    public void initialize() {
        boutons = new Button[]{btn0, btn1, btn2, btn3};
        majTentatives();
        chargerQuestion();
    }

    private void chargerQuestion() {
        labelQuestion.setText("Chargement...");
        labelFeedback.setText("");
        btnSuivant.setVisible(false);
        activerBoutons(false);

        new Thread(() -> {
            try {
                QuizQuestion q = api.fetchQuestion();
                Platform.runLater(() -> {
                    afficherQuestion(q);
                    activerBoutons(true);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    labelQuestion.setText("Impossible de charger une question.");
                    labelFeedback.setText("Vérifiez votre connexion internet.");
                    labelFeedback.setStyle("-fx-text-fill: #ff3c3c; -fx-font-size: 13px; -fx-font-family: 'Georgia'; -fx-font-style: italic;");
                    btnSuivant.setText("RÉESSAYER  →");
                    btnSuivant.setVisible(true);
                });
                e.printStackTrace();
            }
        }).start();
    }

    private void afficherQuestion(QuizQuestion q) {
        questionActuelle = q;
        labelQuestion.setText(q.getQuestion());
        for (int i = 0; i < boutons.length; i++) {
            boutons[i].setText(q.getAllAnswers().get(i));
            boutons[i].setStyle(styleBoutonNormal());
        }
    }

    @FXML
    private void handleReponse(javafx.event.ActionEvent event) {
        Button clique = (Button) event.getSource();
        activerBoutons(false);
        tentatives++;
        majTentatives();

        if (clique.getText().equals(questionActuelle.getCorrectAnswer())) {
            score++;
            labelScore.setText(score + " / 5");
            clique.setStyle(styleBoutonCorrect());
            labelFeedback.setText("✓  Bonne réponse !");
            labelFeedback.setStyle("-fx-text-fill: #4caf50; -fx-font-size: 13px; -fx-font-family: 'Georgia'; -fx-font-style: italic;");
        } else {
            clique.setStyle(styleBoutonIncorrect());
            surlignerBonneReponse();
            labelFeedback.setText("✗  Incorrect — " + questionActuelle.getCorrectAnswer());
            labelFeedback.setStyle("-fx-text-fill: #ff3c3c; -fx-font-size: 13px; -fx-font-family: 'Georgia'; -fx-font-style: italic;");
        }

        if (score >= 5) {
            btnSuivant.setText("DÉSAMORCER  →");
        } else if (tentatives >= MAX_TENTATIVES) {
            labelFeedback.setText("La bombe a explosé... Vous avez échoué.");
            labelFeedback.setStyle("-fx-text-fill: #ff3c3c; -fx-font-size: 14px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
            btnSuivant.setText("GAME OVER  →");
        } else {
            btnSuivant.setText("SUIVANT  →");
        }
        btnSuivant.setVisible(true);
    }

    @FXML
    private void handleSuivant() {
        if (score >= 5) {
            MainApp.loadScene("dialogue_final.fxml");
        } else if (tentatives >= MAX_TENTATIVES) {
            MainApp.loadScene("defaite.fxml");
        } else {
            chargerQuestion();
        }
    }

    private void majTentatives() {
        if (labelTentatives != null)
            labelTentatives.setText("Tentatives : " + tentatives + " / " + MAX_TENTATIVES);
    }

    private void surlignerBonneReponse() {
        for (Button b : boutons)
            if (b.getText().equals(questionActuelle.getCorrectAnswer()))
                b.setStyle(styleBoutonCorrect());
    }

    private void activerBoutons(boolean actif) {
        for (Button b : boutons) b.setDisable(!actif);
    }

    private String styleBoutonNormal() {
        return "-fx-background-color: transparent;" +
                "-fx-border-color: rgba(255,60,60,0.4);" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: rgba(255,255,255,0.8);" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-cursor: hand;";
    }

    private String styleBoutonCorrect() {
        return "-fx-background-color: rgba(76,175,80,0.15);" +
                "-fx-border-color: #4caf50;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: #4caf50;" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: 'Georgia';";
    }

    private String styleBoutonIncorrect() {
        return "-fx-background-color: rgba(255,60,60,0.15);" +
                "-fx-border-color: #ff3c3c;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: #ff3c3c;" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: 'Georgia';";
    }
}
