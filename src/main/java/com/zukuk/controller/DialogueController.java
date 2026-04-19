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

public class DialogueController {

    @FXML private AnchorPane rootPane;
    @FXML private Label      labelReplique;
    @FXML private Label      labelIndice;
    @FXML private Label      labelProgression;
    @FXML private Label      labelBriefing;
    @FXML private ImageView  imageChef;

    private static final String[] REPLIQUES_INTRO = {
            "Écoute-moi bien. Une bombe a été placée quelque part en ville, "
                    + "et tout repose sur toi. Nous n'avons pas de temps à perdre. "
                    + "Chaque seconde compte.",

            "Voici la situation : tu vas devoir résoudre une série d'énigmes. "
                    + "Chacune te donnera des indices pour localiser la bombe. "
                    + "Le temps presse, mais nous avons encore une chance "
                    + "si tu agis rapidement et avec précision.",

            "Je sais que ce n'est pas facile, mais je crois en toi. "
                    + "Nous avons les outils nécessaires, et tu as l'intelligence "
                    + "pour déchiffrer ces énigmes. Chaque réponse correcte "
                    + "nous rapproche de la solution.",

            "Ne laisse pas la pression te faire trébucher. Résous les énigmes, "
                    + "trouve l'emplacement de la bombe, et nous pourrons la désamorcer "
                    + "avant qu'il ne soit trop tard. On compte sur toi. "
                    + "La ville compte sur toi."
    };

    private static final String[] REPLIQUES_INTER1 = {
            "Bien joué. Tu as bien avancé jusqu'ici. Tu as résolu toutes les énigmes, "
                    + "et maintenant, nous avons une meilleure idée de l'endroit où la bombe "
                    + "pourrait être. Mais la tâche n'est pas encore terminée.",

            "Maintenant, il te faut localiser l'emplacement exact. Pour cela, tu vas "
                    + "interroger des suspects. Certains te diront la vérité, d'autres mentiront. "
                    + "Ce sera à toi de discerner qui est fiable et qui ne l'est pas.",

            "Le temps presse. Chaque erreur pourrait nous coûter cher, alors fais attention. "
                    + "Nous n'avons pas de marge pour les hésitations. "
                    + "Trouve où elle se cache, et on pourra passer à l'étape suivante.",

            "Tu es notre seul espoir, et je sais que tu peux le faire. "
                    + "Trouve la bombe, localise-la avec précision. "
                    + "C'est à toi de mener cette mission à bien.",

            "Allez, il ne reste plus beaucoup de temps. "
                    + "Trouve cette bombe, et sauve tout le monde."
    };

    private String[] repliques;
    private String   sceneSuivante;

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

        setDialogue(REPLIQUES_INTRO, "quiz.fxml", "ZUKUK  //  BRIEFING");
    }

    public void setDialogue(String[] repliques, String sceneSuivante, String titreHaut) {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        this.repliques     = repliques;
        this.sceneSuivante = sceneSuivante;
        this.indexReplique = 0;
        this.indexChar     = 0;
        this.etat          = Etat.ECRITURE;
        if (labelBriefing != null) labelBriefing.setText(titreHaut);
        majProgression();
        demarrerReplique();
        Platform.runLater(() -> {
            rootPane.getScene().setOnKeyPressed(this::onKeyPressed);
            rootPane.requestFocus();
        });
    }

    public static String[] getrepliquesInter1() {
        return REPLIQUES_INTER1;
    }

    private void demarrerReplique() {
        labelReplique.setText("");
        indexChar = 0;
        etat      = Etat.ECRITURE;
        labelIndice.setOpacity(0.0);
        majProgression();

        String texte = repliques[indexReplique];

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
                    MainApp.loadScene(sceneSuivante);
                }
                break;
        }
    }

    private void majProgression() {
        if (repliques != null)
            labelProgression.setText((indexReplique + 1) + " / " + repliques.length);
    }
}