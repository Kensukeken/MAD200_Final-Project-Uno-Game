package com.uno;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.media.MediaPlayer;

/**
 * @author: Mahta Haghbin, Hia Al Saleh
 * @date: March 27th, 2025
 * @Filename: Settings.java
 *
 * @Description: This represents the settings screen for the game.
 */
public class Settings extends Application {
    private MediaPlayer gameSound;

    // Constructor to initialize game sound with MediaPlayer
    public Settings(MediaPlayer gameSound) {
        this.gameSound = gameSound;
    }

    // Method to start the settings screen
    @Override
    public void start(Stage stage) {
        Label label = new Label("Settings");
        label.setStyle("-fx-font-weight: bold");
        label.setStyle("-fx-text-fill: red");

        // Created back button to return to the main menu
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Created mute button to mute game sounds
        Button muteButton = new Button("Mute");
        muteButton.getStyleClass().add("pause-button");
        muteButton.setOnAction(e -> {
            if (gameSound != null) {
                gameSound.stop();
            }
        });

        // Created unmute button to unmute game sounds
        Button unmuteButton = new Button("Unmute");
        unmuteButton.getStyleClass().add("play-button");
        unmuteButton.setOnAction(e -> {
            if (gameSound != null) {
                gameSound.play();
            }
        });

        // Set up layout and scene
        VBox layout = new VBox(20, label, muteButton, unmuteButton, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Settings");
    }
}
