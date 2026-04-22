package com.zukuk.service;

import com.google.gson.Gson;
import com.zukuk.model.SaveData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SaveService {

    private static final String SAVE_PATH = "zukuk_save.json";
    private static final Gson   GSON      = new Gson();

    public static void sauvegarder(SaveData data) {
        try (FileWriter writer = new FileWriter(SAVE_PATH)) {
            GSON.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SaveData charger() {
        File file = new File(SAVE_PATH);
        if (!file.exists()) return null;
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, SaveData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean sauvegardeExiste() {
        return new File(SAVE_PATH).exists();
    }

    public static void effacerSauvegarde() {
        new File(SAVE_PATH).delete();
    }
}
