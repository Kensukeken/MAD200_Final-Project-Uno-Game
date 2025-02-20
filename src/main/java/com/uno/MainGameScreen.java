package com.uno;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// Main game screen
public class MainGameScreen {
    public void show(Stage stage) {
        stage.setTitle("UNO - Game");

        BorderPane gameRoot = new BorderPane();

        Scene gameScene = new Scene(gameRoot, 700, 400);
        stage.setScene(gameScene);
    }
}