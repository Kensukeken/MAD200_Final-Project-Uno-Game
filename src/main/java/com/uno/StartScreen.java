package com.uno;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// Start Screen class
public class StartScreen extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UNO");

        // Title label
        Label titleLabel = new Label("Welcome to UNO Game");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setTextFill(Color.RED);

        // Start button
        Button startButton = new Button("Start Game");
        startButton.setFont(new Font("Arial", 24));
        startButton.setOnAction(event -> showLoadingScreen(primaryStage));
        startButton.setStyle("-fx-background-color: #ff0026; -fx-text-fill: #000000;");

        // VBox layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, startButton);

        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoadingScreen(Stage primaryStage) {
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}