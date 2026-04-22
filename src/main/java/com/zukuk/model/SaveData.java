package com.zukuk.model;

public class SaveData {
    private String sceneCourante;

    public SaveData() {}

    public SaveData(String sceneCourante) {
        this.sceneCourante = sceneCourante;
    }

    public String getSceneCourante()         { return sceneCourante; }
    public void   setSceneCourante(String s) { this.sceneCourante = s; }
}
