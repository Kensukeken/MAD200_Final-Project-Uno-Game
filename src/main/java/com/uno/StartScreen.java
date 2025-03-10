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

public class StartScreen extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        Text titleText = new Text("Welcome to UNO Game");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleText.setFill(Color.RED);

        Image image = new Image(getClass().getResourceAsStream("/card.png"));
        ImageView unoCard = new ImageView(image);
        unoCard.setFitWidth(120);
        unoCard.setFitHeight(120);

        VBox topBox = new VBox(10, titleText);
        topBox.setAlignment(Pos.CENTER);
        root.setTop(topBox);

        VBox centerBox = new VBox(unoCard);
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);

        Button startButton = new Button("Click here to get started!");
        startButton.getStyleClass().add("button");

        startButton.setOnAction(event -> {
            try {
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.show(stage);

                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(e -> {
                    try {
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

        animateCard(unoCard);
        animateText(titleText).play();

        stage.setTitle("Welcome to UNO");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void animateCard(ImageView card) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), card);
        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), card);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);

        TranslateTransition move = new TranslateTransition(Duration.seconds(1.5), card);
        move.setFromX(-150);
        move.setToX(0);

        RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), card);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);

        SequentialTransition sequence = new SequentialTransition(fade, scale, move, rotate);
        sequence.setCycleCount(1);
        sequence.setOnFinished(event -> card.setOpacity(1));
        sequence.play();
    }

    private FadeTransition animateText(Text text) {
        FadeTransition fade = new FadeTransition(Duration.seconds(3), text);
        fade.setFromValue(0);
        fade.setToValue(1);
        return fade;
    }

    public static void main(String[] args) {
        launch();
    }
}
