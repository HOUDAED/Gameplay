package com.zukuk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("ZUKUK");
        primaryStage.setResizable(false);
        loadScene("accueil.fxml");
        primaryStage.show();
    }

    public static void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/com/zukuk/view/" + fxmlFile)
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // FIX #4 — donne le focus au root pour que les KeyEvents
            // (ex : ESPACE dans DialogueController) soient bien reçus
            root.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}