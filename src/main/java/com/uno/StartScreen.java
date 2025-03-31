package com.uno;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author: Hia Al Saleh
 * @date: March 17th, 2025
 * @Filename: StartScreen.java
 *
 * @Description: This is class represents our welcome screen with.
 */
public class StartScreen extends Application {
    @Override
    public void start(Stage stage) {
        // Setting up the main stage
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Text title for welcoming screen
        Text titleText = new Text("Welcome to UNO Game");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleText.setFill(Color.RED); 

        // Loading our main attraction - the UNO card
        Image image = new Image(getClass().getResourceAsStream("/card.png"));
        ImageView unoCard = new ImageView(image);
        unoCard.setFitWidth(120);
        unoCard.setFitHeight(120);

        // Setting the title text on top of the screen
        VBox topBox = new VBox(10, titleText);
        topBox.setAlignment(Pos.CENTER);
        root.setTop(topBox);

        // Centers the uno card
        VBox centerBox = new VBox(unoCard);
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);

        // The button that starts the game "Click here to get started!"
        Button startButton = new Button("Click here to get started!");
        startButton.getStyleClass().add("button");

        // What happens when you can't keep out clicking the button
        startButton.setOnAction(event -> {
            try {
                // First we show the loading screen to build anticipation
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.show(stage);

                // Making players wait 5 seconds because suspense is fun
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(e -> {
                    try {
                        // Finally revealing the main menu
                        MainMenu menu = new MainMenu();
                        menu.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                delay.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        root.setBottom(startButton);
        BorderPane.setAlignment(startButton, Pos.CENTER);

        // Lets the animation party begin
        animateCard(unoCard);
        animateText(titleText).play();
        
        stage.setTitle("Welcome to UNO");
        stage.setScene(scene);
        stage.setResizable(false); 
        stage.show();
    }

    // Makes the UNO card do a whole dance routine - fade, grow, slide, and spin
    private void animateCard(ImageView card) {
        // Fade in like a ghost appearing
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), card);
        fade.setFromValue(0);
        fade.setToValue(1);

        // Grow from tiny to normal size like it's on steroids
        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), card);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);

        // Slide in from the left like it's entering a stage
        TranslateTransition move = new TranslateTransition(Duration.seconds(1.5), card);
        move.setFromX(-150);
        move.setToX(0);

        // Do a full spin because why not? 360° of awesomeness
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), card);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);

        // Look after all these moves into one smooth performance
        SequentialTransition sequence = new SequentialTransition(fade, scale, move, rotate);
        sequence.setCycleCount(1);  // One-time show
        sequence.setOnFinished(event -> card.setOpacity(1));  // Making sure it stays visible
        sequence.play();
    }

    /**
     * Smoothly fades in the text.
     * @param text The text to show.
     * @return The fade effect for reuse.
     */

    private FadeTransition animateText(Text text) {
        FadeTransition fade = new FadeTransition(Duration.seconds(3), text);
        fade.setFromValue(0);
        fade.setToValue(1);
        return fade;
    }

    // Main method
    public static void main(String[] args) {
        launch();
    }
}