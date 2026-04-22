package com.zukuk;

import com.zukuk.model.SaveData;
import com.zukuk.service.SaveService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage  primaryStage;
    private static String sceneCourante = "dialogue_intro.fxml";
    private static final String BASE_PATH = "/com/zukuk/view/";

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("ZUKUK");
        primaryStage.setResizable(false);
        loadScene("accueil.fxml");
        primaryStage.show();
    }

    @Override
    public void stop() {
        SaveService.sauvegarder(new SaveData(sceneCourante));
    }

    public static void loadScene(String fxmlFile) {
        try {
            var url = MainApp.class.getResource(BASE_PATH + fxmlFile);
            if (url == null) {
                System.err.println("FXML introuvable : " + BASE_PATH + fxmlFile);
                return;
            }
            sceneCourante = fxmlFile;
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            root.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SaveData getSauvegarde()  { return SaveService.charger(); }
    public static boolean  hasSauvegarde()  { return SaveService.sauvegardeExiste(); }

    public static void main(String[] args) {
        launch(args);
    }
}
