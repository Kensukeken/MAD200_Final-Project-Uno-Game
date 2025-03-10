package com.uno;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameScreenM extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setMinSize(700, 400);

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);

        Text discardLabel = new Text("Discard Pile");
        Rectangle discardPile = new Rectangle(100, 70, Color.RED);

        Text drawLabel = new Text("Draw Pile");
        Rectangle drawPile = new Rectangle(100, 70, Color.BLUE);

        centerBox.getChildren().addAll(discardLabel, discardPile, drawLabel, drawPile);
        root.setCenter(centerBox);

        HBox topPlayer = hPlayerArea("Player 2");
        root.setTop(topPlayer);

        HBox bottomPlayer = hPlayerArea("You");
        root.setBottom(bottomPlayer);

        VBox leftPlayer = vPlayerArea("Player 1");
        root.setLeft(leftPlayer);

        VBox rightPlayer = vPlayerArea("Player 3");
        root.setRight(rightPlayer);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("UNO Game");
        stage.setScene(scene);
        stage.show();
    }

    private HBox hPlayerArea(String playerName) {
        HBox playerBox = new HBox(7);
        playerBox.setAlignment(Pos.CENTER);

        Text playerLabel = new Text(playerName);

        HBox cardBox = new HBox(3);
        for (int i = 0; i < 7; i++) {
            Rectangle card = new Rectangle(40, 60, Color.BLACK);
            cardBox.getChildren().add(card);
        }

        VBox container = new VBox(3, playerLabel, cardBox);
        container.setAlignment(Pos.CENTER);
        playerBox.getChildren().add(container);
        return playerBox;
    }

    private VBox vPlayerArea(String playerName) {
        VBox playerBox = new VBox(3);
        playerBox.setAlignment(Pos.CENTER);

        Text playerLabel = new Text(playerName);

        VBox cardBox = new VBox(3);
        for (int i = 0; i < 7; i++) {
            Rectangle card = new Rectangle(60, 40, Color.BLACK);
            cardBox.getChildren().add(card);
        }

        playerBox.getChildren().addAll(playerLabel, cardBox);
        return playerBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


