package com.uno;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Main Menu class
public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Main Menu");

        // Single player button
        Button singlePlayer = new Button("Single Player");
        singlePlayer.getStyleClass().add("button");
        singlePlayer.setOnAction(e -> showSinglePlayerScreen(primaryStage));

        // Multiplayer button
        Button multiplayer = new Button("Multiplayer");
        multiplayer.setOnAction(e -> showMultiplayerScreen(primaryStage));

        // Credits button
        Button credits = new Button("Credits");
        credits.getStyleClass().add("button");
        credits.setOnAction(e -> showCreditsScreen(primaryStage));

        // Exit button
        Button exit = new Button("Exit");
        exit.getStyleClass().add("button");
        exit.setOnAction(e -> primaryStage.close());

        // VBox layout
        VBox vbox = new VBox(20, singlePlayer, multiplayer, credits, exit);
        vbox.setAlignment(Pos.CENTER);

        // Scene
        Scene scene = new Scene(vbox, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        scene.setFill(Color.VIOLET);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    // Show single player screen
    private void showSinglePlayerScreen(Stage stage) {
        SinglePlayerScreen singlePlayerScreen = new SinglePlayerScreen();
        singlePlayerScreen.start(stage);
    }

    // Show multiplayer screen
    private void showMultiplayerScreen(Stage stage) {
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen();
        multiplayerScreen.start(stage);
    }

    // Show credits screen
    private void showCreditsScreen(Stage stage) {
        CreditsScreen creditsScreen = new CreditsScreen();
        creditsScreen.start(stage);
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}