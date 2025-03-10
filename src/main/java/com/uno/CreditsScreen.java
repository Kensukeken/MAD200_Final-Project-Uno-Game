package com.uno;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreditsScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 400, Color.BLACK);

        // Background image setup
        ImageView background = new ImageView(loadImage("background.png"));
        background.setFitWidth(700);
        background.setFitHeight(400);
        background.setPreserveRatio(false);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        background.setEffect(colorAdjust);
        StackPane backgroundPane = new StackPane(background);
        root.setCenter(backgroundPane);

        // Credits content (cards and text) inside a VBox
        VBox creditsBox = new VBox(20);
        creditsBox.setAlignment(Pos.CENTER);

        // Card images and their container
        HBox cardBox = new HBox(20);
        cardBox.setAlignment(Pos.CENTER);
        ImageView card1 = createCard("red_card.png");
        ImageView card2 = createCard("green_card.png");
        ImageView card3 = createCard("blue_card.png");
        ImageView card4 = createCard("yellow_card.png");
        cardBox.getChildren().addAll(card1, card2, card3, card4);

        // Credits text
        Text projectText = createText("Uno Game Project", 24);
        Text contributorsText = createText("Contributors: Hia Al Saleh, Mahta Haghbin, Yuanyang Chen", 18);
        Text resourcesText = createText("Resources: SceneBuilder, JavaFX Loading Screen, JavaFX Animation Library", 18);
        Text assetsText = createText("Image Assets: Creazilla UNO Cards, Mattel UNO Game Show Plans", 18);
        VBox textBox = new VBox(10,  projectText, contributorsText, resourcesText, assetsText);
        textBox.setAlignment(Pos.CENTER);
        textBox.setOpacity(0);

        creditsBox.getChildren().addAll(cardBox, textBox);
        backgroundPane.getChildren().add(creditsBox);

        // Back Button placed at the top-left corner
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            // Disable the button after it's clicked to prevent repeated clicks
            backButton.setDisable(true);
            try {
                new MainMenu().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the back button positioned at the top-left
        HBox backButtonContainer = new HBox(backButton);
        backButtonContainer.setAlignment(Pos.TOP_LEFT);
        root.setTop(backButtonContainer);  // Set back button at the top-left corner

        // Animation Sequence (without the final fade-out)
        ParallelTransition cardRotateAnimation = myCardRotation(card1, card2, card3, card4);
        FadeTransition textFadeInAnimation = new FadeTransition(Duration.seconds(2), textBox);
        textFadeInAnimation.setFromValue(0);
        textFadeInAnimation.setToValue(1);
        textFadeInAnimation.setInterpolator(Interpolator.EASE_IN);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));

        SequentialTransition fullAnimation = new SequentialTransition(
                cardRotateAnimation,
                textFadeInAnimation,
                pause
        );
        fullAnimation.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Uno Game Credits");
        primaryStage.show();
    }

    // Helper: Create a parallel rotation animation for multiple card ImageViews.
    private ParallelTransition myCardRotation(ImageView card1, ImageView card2, ImageView card3, ImageView card4) {
        ParallelTransition parallelTransition = new ParallelTransition();
        for (ImageView card : new ImageView[]{card1, card2, card3, card4}) {
            RotateTransition rotate = new RotateTransition(Duration.seconds(2), card);
            rotate.setByAngle(360);
            rotate.setCycleCount(2);
            rotate.setInterpolator(Interpolator.EASE_BOTH);
            parallelTransition.getChildren().add(rotate);
        }
        return parallelTransition;
    }

    // Helper: Create an ImageView for a card image.
    private ImageView createCard(String imagePath) {
        ImageView card = new ImageView(loadImage(imagePath));
        card.setFitWidth(120);
        card.setFitHeight(180);
        return card;
    }

    // Helper: Load an image resource with error handling.
    private Image loadImage(String imagePath) {
        try {
            return new Image(getClass().getResourceAsStream("/" + imagePath));
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

    // Helper: Create a Text node with a custom font and random color.
    private Text createText(String content, int size) {
        Text text = new Text(content);
        text.setFont(Font.font("Arial", FontWeight.BOLD, size));
        text.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        return text;
    }

    // Main Method
    public static void main(String[] args) {
        launch(args);
    }
}
