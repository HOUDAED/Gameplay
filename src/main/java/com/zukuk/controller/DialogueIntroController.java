package com.zukuk.controller;

public class DialogueIntroController extends DialogueController {

    private static final String[] REPLIQUES = {
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

    @Override
    protected String[] getRepliques()      { return REPLIQUES; }

    @Override
    protected String getSceneSuivante()    { return "quiz.fxml"; }

    @Override
    protected String getTitreHaut()        { return "ZUKUK  //  BRIEFING"; }
}
