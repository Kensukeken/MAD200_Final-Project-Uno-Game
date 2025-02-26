package com.uno;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Main Menu");
        title.getStyleClass().add("title");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        title.setTextFill(Color.RED);
        title.setFont(Font.font("Times New Roman", 24));

        // Buttons for main menu
        Button singlePlayer = scaleButton("Single Player", e -> showSinglePlayerScreen(primaryStage));
        Button multiplayer = scaleButton("Multiplayer", e -> showMultiplayerScreen(primaryStage));
        Button credits = scaleButton("Credits", e -> showCreditsScreen(primaryStage));
        Button exit = scaleButton("Exit", e -> primaryStage.close());

        // VBox layout for main menu
        VBox vbox = new VBox(20, title, singlePlayer, multiplayer, credits, exit);
        vbox.setAlignment(Pos.CENTER);

        // Scene for main menu
        Scene scene = new Scene(vbox, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Load external CSS file
        scene.setFill(Color.VIOLET);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    // These methods are used to create the buttons for the main menu
    private Button scaleButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.getStyleClass().add("button");
        button.setOnAction(eventHandler);

        // setOnMouseEntered is used for hover effect on buttons
        button.setOnMouseEntered(event -> {
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);
            scaleUp.play();
        });

        // setOnMouseExited is used for hover effect on buttons
        button.setOnMouseExited(event -> {
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), button);
            scaleDown.setToX(1);
            scaleDown.setToY(1);
            scaleDown.play();
        });

        return button;
    }

    // These methods are used to display the corresponding screens when a button is clicked.
    // The screens are:
    /*
     - Single player screen
     - Multiplayer screen
     - Credits screen
    */

    private void showSinglePlayerScreen(Stage stage) {
        SinglePlayerScreen singlePlayerScreen = new SinglePlayerScreen();
        singlePlayerScreen.start(stage);
    }

    private void showMultiplayerScreen(Stage stage) {
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen();
        multiplayerScreen.start(stage);
    }

    private void showCreditsScreen(Stage stage) {
        CreditsScreen creditsScreen = new CreditsScreen();
        creditsScreen.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
