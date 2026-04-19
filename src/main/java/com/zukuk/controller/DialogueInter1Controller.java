package com.zukuk.controller;

public class DialogueInter1Controller extends DialogueController {

    private static final String[] REPLIQUES = {
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

    @Override
    protected String[] getRepliques()      { return REPLIQUES; }

    @Override
    protected String getSceneSuivante()    { return "quiz2.fxml"; }

    @Override
    protected String getTitreHaut()        { return "ZUKUK  //  LOCALISATION"; }
}
