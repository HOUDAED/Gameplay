package com.zukuk.controller;

public class DialogueFinalController extends DialogueController {

    private static final String[] REPLIQUES = {
        "Tu l'as fait… Tu as réussi à désamorcer la bombe et à sauver la ville. "
        + "Je savais que tu en étais capable.",

        "Grâce à toi, des vies ont été sauvées aujourd'hui. "
        + "Tu as fait preuve de courage, d'intelligence et de détermination "
        + "dans chaque étape de cette mission. Tu as tout donné, et ça a payé.",

        "Bien joué, vraiment. Tu as prouvé qu'il n'y a rien que tu ne puisses accomplir. "
        + "Je n'oublierai jamais ce jour."
    };

    @Override
    protected String[] getRepliques()   { return REPLIQUES; }

    @Override
    protected String getSceneSuivante() { return "victoire.fxml"; }

    @Override
    protected String getTitreHaut()     { return "ZUKUK  //  VICTOIRE"; }
}
