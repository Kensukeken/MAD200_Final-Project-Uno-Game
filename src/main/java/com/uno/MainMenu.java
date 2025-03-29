package com.uno;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;

/**
 * @author: Hia Al Saleh, Mahta Haghbin
 * @date: February 24th, 2025
 * @Filename: MainMenu.java
 *
 * @Description: This represents the main menu screen on the game.
 */
public class MainMenu extends Application {
    private MediaPlayer backPlayer1 = null;

    @Override
    public void start(Stage primaryStage) {
        File folder = new File("Music/");
        Label title = new Label("Main Menu");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        title.setTextFill(Color.RED);

        // Creating our menu buttons with all the cool hover effects
        Button singlePlayer = myMenuButton("Play", e -> showPlayerScreen(primaryStage));
        Button multiplayer = myMenuButton("Uno Rules", e -> showUnoRulesScreen(primaryStage));
        Button settingsButton = myMenuButton("Settings", e -> {
            if (backPlayer1 != null) {
                backPlayer1.stop();
            }
            Settings settingsScreen = new Settings(backPlayer1);
            settingsScreen.start(primaryStage);
        });
        Button credits = myMenuButton("Credits", e -> showCreditsScreen(primaryStage));
        Button exit = myMenuButton("Exit", e -> primaryStage.close());

        // Created left panel for buttons with increased left padding
        VBox leftPanel = new VBox(20, title, singlePlayer, multiplayer, settingsButton, credits, exit);
        leftPanel.setAlignment(Pos.CENTER_LEFT);
        leftPanel.setPadding(new Insets(20, 20, 20, 60));

        // Created right panel for UNO card image
        ImageView unoCard = new ImageView(new Image(getClass().getResourceAsStream("/uno.png")));
        unoCard.setPreserveRatio(true);
        unoCard.setFitWidth(250);

        // StackPane for the uno card
        StackPane rightPanel = new StackPane(unoCard);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.CENTER);

        // Created main layout
        BorderPane root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(rightPanel);
        root.setBackground(new Background(new BackgroundFill(Color.VIOLET, CornerRadii.EMPTY, Insets.EMPTY)));

        // Setting up our stage
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        //  Here goes the music section
        Media backMusic1 = new Media(new File("Music/Definition of Harmony.mp3").toURI().toString());
        backPlayer1 = new MediaPlayer(backMusic1);
        backPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        backPlayer1.play();

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
        button.getStyleClass().add("button");
        button.setOnAction(handler);
        button.setPrefWidth(150);

        // Making it grow when you hover
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

    // Takes you to the player setup screen.
    private void showPlayerScreen(Stage stage) {
        PlayerScreen screen = new PlayerScreen();
        screen.start(stage);
    }

    // Method to show Uno rules
    private void showUnoRulesScreen(Stage stage) {
        UnoRules screen = new UnoRules();
        screen.start(stage);
    }

    // Method to show the creditsScreen
    private void showCreditsScreen(Stage stage) {
        CreditsScreen screen = new CreditsScreen();
        screen.start(stage);
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}