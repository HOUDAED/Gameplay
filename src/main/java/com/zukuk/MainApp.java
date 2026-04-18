package com.zukuk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}