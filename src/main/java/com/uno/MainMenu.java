package com.uno;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Main Menu");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        title.setTextFill(Color.RED);

        Button singlePlayer = createMenuButton("Single Player", e -> showSinglePlayerScreen(primaryStage));
        Button multiplayer = createMenuButton("Multiplayer", e -> showMultiplayerScreen(primaryStage));
        Button credits = createMenuButton("Credits", e -> showCreditsScreen(primaryStage));
        Button exit = createMenuButton("Exit", e -> primaryStage.close());

        VBox layout = new VBox(20, title, singlePlayer, multiplayer, credits, exit);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        scene.setFill(Color.VIOLET);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    private Button createMenuButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.getStyleClass().add("button");
        button.setOnAction(handler);

        button.setOnMouseEntered(event -> {
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);
            scaleUp.play();
        });

        button.setOnMouseExited(event -> {
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), button);
            scaleDown.setToX(1);
            scaleDown.setToY(1);
            scaleDown.play();
        });

        return button;
    }

    private void showSinglePlayerScreen(Stage stage) {
        SinglePlayerScreen screen = new SinglePlayerScreen();
        screen.start(stage);
    }

    private void showMultiplayerScreen(Stage stage) {
        MultiplayerScreen screen = new MultiplayerScreen();
        screen.start(stage);
    }

    private void showCreditsScreen(Stage stage) {
        CreditsScreen screen = new CreditsScreen();
        screen.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
