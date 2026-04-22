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

public class QuizController {

    @FXML protected AnchorPane rootPane;
    @FXML protected Label      labelScore;
    @FXML protected Label      labelQuestion;
    @FXML protected Label      labelFeedback;
    @FXML protected Button     btn0, btn1, btn2, btn3;
    @FXML protected Button     btnSuivant;
    @FXML protected GridPane   gridReponses;

    protected final QuizApiService api = new QuizApiService();
    protected QuizQuestion questionActuelle;
    protected int score = 0;
    protected Button[] boutons;
    protected String sceneSuivante = "";

    @FXML
    public void initialize() {
        boutons = new Button[]{btn0, btn1, btn2, btn3};
        chargerQuestion();
    }

    protected void chargerQuestion() {
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
        String reponse = clique.getText();
        activerBoutons(false);

        if (reponse.equals(questionActuelle.getCorrectAnswer())) {
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

        btnSuivant.setText(score >= 5 ? "CONTINUER  →" : "SUIVANT  →");
        btnSuivant.setVisible(true);
    }

    @FXML
    protected void handleSuivant() {
        if (score >= 5) {
            MainApp.loadScene(sceneSuivante);
        } else {
            chargerQuestion();
        }
    }

    private void surlignerBonneReponse() {
        for (Button b : boutons)
            if (b.getText().equals(questionActuelle.getCorrectAnswer()))
                b.setStyle(styleBoutonCorrect());
    }

    private void activerBoutons(boolean actif) {
        for (Button b : boutons) b.setDisable(!actif);
    }

    protected String styleBoutonNormal() {
        return "-fx-background-color: transparent;" +
                "-fx-border-color: rgba(255,60,60,0.4);" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: rgba(255,255,255,0.8);" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-cursor: hand;";
    }

    protected String styleBoutonCorrect() {
        return "-fx-background-color: rgba(76,175,80,0.15);" +
                "-fx-border-color: #4caf50;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: #4caf50;" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: 'Georgia';";
    }

    protected String styleBoutonIncorrect() {
        return "-fx-background-color: rgba(255,60,60,0.15);" +
                "-fx-border-color: #ff3c3c;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: #ff3c3c;" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: 'Georgia';";
    }
}