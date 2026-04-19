package com.zukuk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static final String BASE_PATH = "/com/zukuk/view/";

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
            var url = MainApp.class.getResource(BASE_PATH + fxmlFile);

            if (url == null) {
                System.err.println("FXML introuvable : " + BASE_PATH + fxmlFile);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            root.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}