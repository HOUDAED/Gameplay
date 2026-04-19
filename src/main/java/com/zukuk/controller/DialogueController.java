package com.zukuk.controller;

import com.zukuk.MainApp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class DialogueController {

    @FXML protected AnchorPane rootPane;
    @FXML protected Label      labelReplique;
    @FXML protected Label      labelIndice;
    @FXML protected Label      labelProgression;
    @FXML protected Label      labelBriefing;
    @FXML protected ImageView  imageChef;

    protected abstract String[] getRepliques();
    protected abstract String   getSceneSuivante();
    protected abstract String   getTitreHaut();

    private String[] repliques;
    private int      indexReplique = 0;
    private int      indexChar     = 0;
    private Timeline timeline;

    private enum Etat { ECRITURE, COMPLETE }
    private Etat etat = Etat.ECRITURE;

    @FXML
    public void initialize() {
        try {
            Image img = new Image(
                    getClass().getResourceAsStream("/com/zukuk/images/chef.png")
            );
            imageChef.setImage(img);
            Rectangle clip = new Rectangle(300, 320);
            imageChef.setClip(clip);
        } catch (Exception e) {}

        repliques = getRepliques();
        if (labelBriefing != null) labelBriefing.setText(getTitreHaut());
        majProgression();

        Platform.runLater(() -> {
            demarrerReplique();
            rootPane.getScene().setOnKeyPressed(this::onKeyPressed);
            rootPane.requestFocus();
        });
    }

    private void demarrerReplique() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }

        indexChar = 0;
        etat      = Etat.ECRITURE;
        labelIndice.setOpacity(0.0);
        labelReplique.setText("");
        majProgression();

        String texte = repliques[indexReplique];

        // Platform.runLater garantit que le label est bien vidé
        // avant que l'animation commence
        Platform.runLater(() -> {
            timeline = new Timeline(new KeyFrame(Duration.millis(28), e -> {
                if (indexChar < texte.length()) {
                    labelReplique.setText(texte.substring(0, ++indexChar));
                } else {
                    timeline.stop();
                    etat = Etat.COMPLETE;
                    labelIndice.setOpacity(1.0);
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if (timeline == null || repliques == null) return;
        if (event.getCode() != KeyCode.SPACE) return;

        switch (etat) {
            case ECRITURE:
                timeline.stop();
                labelReplique.setText(repliques[indexReplique]);
                etat = Etat.COMPLETE;
                labelIndice.setOpacity(1.0);
                break;

            case COMPLETE:
                indexReplique++;
                if (indexReplique < repliques.length) {
                    demarrerReplique();
                } else {
                    MainApp.loadScene(getSceneSuivante());
                }
                break;
        }
    }

    private void majProgression() {
        if (repliques != null)
            labelProgression.setText((indexReplique + 1) + " / " + repliques.length);
    }
}