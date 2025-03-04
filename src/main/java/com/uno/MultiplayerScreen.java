package com.uno;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

// Multiplayer screen
public class MultiplayerScreen {
    public void start(Stage stage) {
        Label label = new Label("Multiplayer Mode");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        backButton.setOnAction(e -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(20, label, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Multiplayer");
    }
}