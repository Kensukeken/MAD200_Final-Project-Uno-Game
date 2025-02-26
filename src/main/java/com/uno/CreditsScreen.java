package com.uno;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

// Credits screen
public class CreditsScreen {
    public void start(Stage stage) {
        Label titleLabel = new Label("Credits");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label dev1Label = new Label("Developer 1: Mahta Haghbin");
        Label dev2Label = new Label("Developer 2: Hia Al Saleh");
        Label dev3Label = new Label("Developer 3: Yuanyang Chen");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(20, titleLabel, dev1Label, dev2Label, dev3Label, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Credits");
    }
}