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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author: Hia Al Saleh
 * @date: February 24th, 2025
 * @Filename: MainMenu.java
 *
 * @Description: This represents the main menu screen on the game.
 */
public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Main Menu");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        title.setTextFill(Color.RED);

        // Creating our menu buttons with all the cool hover effects
        Button singlePlayer = myMenuButton("Play", e -> showPlayerScreen(primaryStage));
        Button multiplayer = myMenuButton("Uno Rules", e -> showUnoRulesScreen(primaryStage));
        Button credits = myMenuButton("Credits", e -> showCreditsScreen(primaryStage));
        Button exit = myMenuButton("Exit", e -> primaryStage.close());

        // Arranging everything neatly in the center
        VBox layout = new VBox(20, title, singlePlayer, multiplayer, credits, exit);
        layout.setAlignment(Pos.CENTER);

        // Setting up our stage with a cool violet background
        Scene scene = new Scene(layout, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        scene.setFill(Color.VIOLET);

        // Showtime!
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    /**
     * Creates a fancy button that grows when you hover over it.
     * @param text What the button says
     * @param handler What happens when you click it
     * @return A button that's way more exciting than regular buttons
     */
    private Button myMenuButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.getStyleClass().add("button");  // Making it pretty with CSS
        button.setOnAction(handler);  // Setting up its purpose in life

        // Making it grow when you hover - because we're extra
        button.setOnMouseEntered(event -> {
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);
            scaleUp.play();
        });

        // Bringing it back to normal size when you leave
        button.setOnMouseExited(event -> {
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), button);
            scaleDown.setToX(1);
            scaleDown.setToY(1);
            scaleDown.play();
        });

        return button;
    }

    /**
     * Takes you to the player setup screen.
     * @param stage Our main stage - we're just changing the scene
     */
    private void showPlayerScreen(Stage stage) {
        PlayerScreen screen = new PlayerScreen();
        screen.start(stage);
    }

    /**
     * Shows the UNO rules because someone always forgets how to play.
     * @param stage Our main stage
     */
    private void showUnoRulesScreen(Stage stage) {
        UnoRules screen = new UnoRules();
        screen.start(stage);
    }

    /**
     * Shows who made this awesome game.
     * @param stage Our main stage
     */
    private void showCreditsScreen(Stage stage) {
        CreditsScreen screen = new CreditsScreen();
        screen.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}