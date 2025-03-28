package com.uno;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.media.AudioClip;
/**
 * @author: Mahta Haghbin
 * @date: March 27th, 2025
 * @Filename: Settings.java
 *
 * @Description: This represents the settings screen for the game.
 * */
public class Settings {
    private AudioClip gameSound; // Added AudioClip for game sound

    // Constructor to initialize game sound
    public void SettingsScreen(AudioClip gameSound) {
        this.gameSound = gameSound;
    }

    // Method to start the settings screen
    public void start(Stage stage) {
        Label label = new Label("Settings");

        // Created back button to return to pause screen
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            PauseScreen pauseScreen = new PauseScreen();
            pauseScreen.start(stage, backButton, () -> {});
        });

        // Created pause button to mute game sounds
        Button pauseButton = new Button("Mute");
        pauseButton.getStyleClass().add("pause-button");
        pauseButton.setOnAction(e -> {
            if (gameSound != null) {
                gameSound.stop(); // Mute the game sound
            }
        });

        // Created play button to unmute game sounds
        Button playButton = new Button("Unmute");
        playButton.getStyleClass().add("play-button");
        playButton.setOnAction(e -> {
            if (gameSound != null) {
                gameSound.play(); // Unmute the game sound
            }
        });

        // Set up layout and scene
        VBox layout = new VBox(20, label, pauseButton, playButton, backButton); // Added new buttons to layout
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Ensure styles.css exists and is correctly referenced
        stage.setScene(scene);
        stage.setTitle("Settings");
    }
}
