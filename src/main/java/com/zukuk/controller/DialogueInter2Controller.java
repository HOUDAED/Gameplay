package com.zukuk.controller;

public class DialogueInter2Controller extends DialogueController {

    private static final String[] REPLIQUES = {
        "Tu as trouvé l'emplacement de la bombe. C'est un soulagement, "
        + "mais ne te repose pas encore. Le plus difficile reste à venir.",

        "Nous savons maintenant où elle se trouve, mais il faut encore la désamorcer. "
        + "C'est une course contre la montre. Si tu échoues à désactiver la bombe, "
        + "tout est fini. Mais je sais que tu as ce qu'il faut pour y arriver.",

        "Tu as déjà prouvé que tu es capable de prendre les bonnes décisions. "
        + "Ne laisse pas cette dernière étape t'intimider. "
        + "C'est à toi de jouer maintenant. La ville, les gens, tout dépend de toi.",

        "Bonne chance. Et rappelle-toi, le destin de tout le monde est entre tes mains."
    };

    @Override
    protected String[] getRepliques()   { return REPLIQUES; }

    @Override
    protected String getSceneSuivante() { return "quiz3.fxml"; }

    @Override
    protected String getTitreHaut()     { return "ZUKUK  //  DÉSAMORÇAGE"; }
}
