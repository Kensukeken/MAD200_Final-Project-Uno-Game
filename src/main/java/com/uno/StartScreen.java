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
 * @Description: This is where the magic begins - our welcome screen with 
 * sick animations that make the UNO card dance and the text fade in smooth. 
 * It's like the opening scene of a blockbuster movie but for our UNO game.
 */
public class StartScreen extends Application {
    @Override
    public void start(Stage stage) {
        // Setting up the main stage - our VIP lounge for UNO
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Big flashy title because subtlety is overrated
        Text titleText = new Text("Welcome to UNO Game");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleText.setFill(Color.RED); 

        // Loading our main attraction - the UNO card
        Image image = new Image(getClass().getResourceAsStream("/card.png"));
        ImageView unoCard = new ImageView(image);
        unoCard.setFitWidth(120);
        unoCard.setFitHeight(120);

        // Positioning everything center stage like a diva
        VBox topBox = new VBox(10, titleText);
        topBox.setAlignment(Pos.CENTER);
        root.setTop(topBox);

        VBox centerBox = new VBox(unoCard);
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);

        // The big shiny button that starts the adventure
        Button startButton = new Button("Click here to get started!");
        startButton.getStyleClass().add("button");

        // What happens when you can't resist clicking the button
        startButton.setOnAction(event -> {
            try {
                // First we show the loading screen to build anticipation
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.show(stage);

                // Making players wait 5 seconds because suspense is fun
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(e -> {
                    try {
                        // Finally revealing the main menu like a boss
                        MainMenu menu = new MainMenu();
                        menu.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();  // Just in case things go sideways
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

    /**
     * Makes the UNO card do a whole dance routine - fade, grow, slide, and spin
     * @param card The lucky card that gets to show off
     */
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

        // Choreographing all these moves into one smooth performance
        SequentialTransition sequence = new SequentialTransition(fade, scale, move, rotate);
        sequence.setCycleCount(1);  // One-time show
        sequence.setOnFinished(event -> card.setOpacity(1));  // Making sure it stays visible
        sequence.play();
    }

    /**
     * Makes the text fade in all smooth and classy
     * @param text The text that's about to make an entrance
     * @return The fade transition because we might want to reuse it
     */
    private FadeTransition animateText(Text text) {
        FadeTransition fade = new FadeTransition(Duration.seconds(3), text);
        fade.setFromValue(0);  // Starting invisible
        fade.setToValue(1);    // Ending fully visible
        return fade;
    }

    // The classic main method - the ignition to start our engine
    public static void main(String[] args) {
        launch();
    }
}