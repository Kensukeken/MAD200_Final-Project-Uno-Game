package com.uno;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
/**
 * @author: Hia Al Saleh, Mahta Haghbin
 * @date: March 24th, 2025
 * @Filename: PauseScreen.java
 *
 * @Description: This represents the pause screen on the game logic.
 * */
public class PauseScreen {
    public void start(Stage stage, Button backButton, Runnable onResume) {
        Label label = new Label("Game Paused");

        // Created resume button
        Button resumeButton = new Button("Resume");
        resumeButton.getStyleClass().add("resume-button");
        resumeButton.setOnAction(e -> onResume.run());

        // Configure back button with error handling
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            try {
                // Go back to main menu
                MainMenu mainMenu = new MainMenu();
                mainMenu.start(stage);
            } catch (Exception ex) {
                System.err.println("Error returning to main menu: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Created settings button
        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("settings-button");
        settingsButton.setOnAction(e -> {
            // Logic to open settings screen
            Settings settingsScreen = new Settings();
            settingsScreen.start(stage);
        });
        // Created rules button
        Button rulesButton = new Button("Rules");
        rulesButton.getStyleClass().add("rules-button");
        rulesButton.setOnAction(e -> {
            // Logic to open rules screen
            UnoRules rulesScreen = new UnoRules();
            rulesScreen.start(stage);
        });
        // Created quit button
        Button quitButton = new Button("Quit");
        quitButton.getStyleClass().add("quit-button");
        quitButton.setOnAction(e -> {
            // Logic to quit the game
            stage.close(); // Close the stage to quit the game
        });


        VBox layout = new VBox(20, label, resumeButton, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Pause");
    }
}