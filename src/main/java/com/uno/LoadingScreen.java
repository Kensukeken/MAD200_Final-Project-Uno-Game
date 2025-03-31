package com.uno;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author: Hia Al Saleh
 * @date: March 10th, 2025
 * @Filename: LoadingScreen.java
 *
 * @Description: This is a loading screen represents the loading after the starter screen.
 * */
public class LoadingScreen {
    public void show(Stage stage) {
        stage.setTitle("Loading...");

        // Progress bar
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(-1.0);

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(progressBar);

        // Scene
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.show();

        // Pause Transition makes the loading screen visible for 2 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> showMainMenu(stage));
        delay.play();
    }

    // Shows the main menu after the loading screen
    private void showMainMenu(Stage stage) {
        MainMenu mainMenu = new MainMenu();
        try {
            mainMenu.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}