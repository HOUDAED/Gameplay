package com.zukuk.controller;

import com.zukuk.MainApp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class DialogueController {

    @FXML private Label     labelReplique;
    @FXML private Label     labelIndice;
    @FXML private Label     labelProgression;
    @FXML private ImageView imageChef;

    private final String[] repliques = {
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
        } catch (Exception e) {

        }
        majProgression();
        demarrerReplique();
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
                    MainApp.loadScene("quiz.fxml");
                }
                break;
        }
    }


    private void majProgression() {
        labelProgression.setText((indexReplique + 1) + " / " + repliques.length);
    }
}