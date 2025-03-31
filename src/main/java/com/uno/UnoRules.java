package com.uno;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
/**
 * @author: Hia Al Saleh
 * @date: March 24th, 2025
 * @Filename: UnoRules.java

 * @Description: Shows the UNO game rules as an image with scrollPane
 */
public class UnoRules {
    public void start(Stage stage) {
        Label label = new Label("How To Play");
        label.getStyleClass().add("title-label");

        // Loading the UNO rules image
        Image rulesImage = new Image(getClass().getResourceAsStream("/uno_rules.png"));
        ImageView imageView = new ImageView(rulesImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);

        // Centering the image
        StackPane centeredImage = new StackPane(imageView);
        centeredImage.setAlignment(Pos.CENTER);
        centeredImage.setPadding(new Insets(10));

        // Setting up scrollPane in case someone's on a tiny screen (assume I have Windows 10 if that makes sense)
        ScrollPane scrollPane = new ScrollPane(centeredImage);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // No horizontal scroll
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Vertical only when needed
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Putting everything together in a nice layout
        VBox layout = new VBox(10, label, scrollPane, backButton);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(layout, 800, 650);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Uno Rules");
        stage.centerOnScreen();
    }
}