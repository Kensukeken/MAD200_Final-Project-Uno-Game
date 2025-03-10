package com.uno;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;

public class SinglePlayerScreen extends Application {
    private List<Card> playerHand;
    private Card discardPileTop;
    private ImageView discardPileView;
    private ImageView drawPileView;
    private GameLogic gameLogic;
    private BorderPane root;
    private Label turnIndicator;
    private Label lastMoveIndicator;
    private TextArea debugText;

    @Override
    public void start(Stage stage) {
        gameLogic = new GameLogic();
        gameLogic.initializeOneVsThreeComputerGame();

        playerHand = gameLogic.getCurrentPlayer().getHand();
        discardPileTop = gameLogic.getTopCard();

        root = new BorderPane();
        root.setMinSize(700, 500);

        turnIndicator = new Label("Turn: " + gameLogic.getCurrentPlayer().getName());
        turnIndicator.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        lastMoveIndicator = new Label("Last Move: No moves yet.");
        lastMoveIndicator.setStyle("-fx-font-size: 14px;");

        debugText = new TextArea();
        debugText.setEditable(false);
        debugText.setPrefHeight(80);
        debugText.setStyle("-fx-font-size: 12px;");
        debugText.setText("Game Started\n");

        VBox centerBox = new VBox(5);
        centerBox.setAlignment(Pos.CENTER);

        Text discardLabel = new Text("Discard Pile");
        discardPileView = createImageView(discardPileTop);

        Text drawLabel = new Text("Draw Pile");
        drawPileView = createHiddenDrawPileImage();

        centerBox.getChildren().addAll(turnIndicator, lastMoveIndicator, discardLabel, discardPileView, drawLabel, drawPileView);
        root.setCenter(centerBox);

        root.setTop(createHorizontalPlayerArea("Player 2"));
        root.setBottom(new VBox(5, createHorizontalPlayerArea("You"), debugText));
        root.setLeft(createVerticalPlayerArea("Player 1"));
        root.setRight(createVerticalPlayerArea("Player 3"));

        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("UNO Game - Single Player");
        stage.setScene(scene);
        stage.show();
    }

    private ImageView createImageView(Card card) {
        ImageView imageView = new ImageView(CardLoader.loadCardImage(card.getPossibleImageNames()[0]));
        imageView.setFitWidth(50);
        imageView.setFitHeight(75);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(event -> handleCardClick(card));
        return imageView;
    }

    private ImageView createHiddenDrawPileImage() {
        ImageView drawPileView = new ImageView(CardLoader.loadCardImage("uno_0_card.png"));
        drawPileView.setFitWidth(50);
        drawPileView.setFitHeight(75);
        drawPileView.setPreserveRatio(true);

        drawPileView.setOnMouseClicked(event -> {
            Card drawnCard = gameLogic.drawFromDeck(gameLogic.getCurrentPlayer());
            if (drawnCard != null) {
                playerHand.add(drawnCard);
                lastMoveIndicator.setText("You drew: " + drawnCard);
                debugText.appendText("You drew: " + drawnCard + "\n");
            } else {
                lastMoveIndicator.setText("Deck is empty! No card drawn.");
                debugText.appendText("Deck is empty! No card drawn.\n");
            }
            refreshUI();
        });

        return drawPileView;
    }

    private void handleCardClick(Card clickedCard) {
        if (gameLogic.isValidMove(clickedCard, gameLogic.getTopCard())) {
            discardPileTop = clickedCard;
            discardPileView.setImage(CardLoader.loadCardImage(discardPileTop.getPossibleImageNames()[0]));
            playerHand.remove(clickedCard);
            lastMoveIndicator.setText("You played: " + clickedCard);
            debugText.appendText("You played: " + clickedCard + "\n");

            if (clickedCard.getSuit().equals("wild")) {
                showColorSelection();
                return;
            }

            refreshUI();
            updateTurn();
        } else {
            showInvalidMoveAlert();
        }
    }

    private void showInvalidMoveAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Move");
        alert.setHeaderText(null);
        alert.setContentText("Invalid move! Choose another card.");
        alert.showAndWait();
    }

    private void updateTurn() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            gameLogic.nextTurn();
            turnIndicator.setText("Turn: " + gameLogic.getCurrentPlayer().getName());
            debugText.appendText("Turn: " + gameLogic.getCurrentPlayer().getName() + "\n");
            refreshUI();

            if (!gameLogic.getCurrentPlayer().getName().equals("You")) {
                playAI();
            }
        });
        delay.play();
    }

    private void playAI() {
        PauseTransition aiDelay = new PauseTransition(Duration.seconds(1));
        aiDelay.setOnFinished(ev -> {
            Card aiPlayedCard = gameLogic.aiPlayCard();
            if (aiPlayedCard != null) {
                discardPileTop = aiPlayedCard;
                discardPileView.setImage(CardLoader.loadCardImage(aiPlayedCard.getPossibleImageNames()[0]));
                lastMoveIndicator.setText(gameLogic.getCurrentPlayer().getName() + " played: " + aiPlayedCard);
                debugText.appendText(gameLogic.getCurrentPlayer().getName() + " played: " + aiPlayedCard + "\n");
            } else {
                lastMoveIndicator.setText(gameLogic.getCurrentPlayer().getName() + " drew a card.");
                debugText.appendText(gameLogic.getCurrentPlayer().getName() + " drew a card.\n");
            }
            refreshUI();
            updateTurn();
        });
        aiDelay.play();
    }

    private void refreshUI() {
        root.setTop(createHorizontalPlayerArea("Player 2"));
        root.setBottom(new VBox(5, createHorizontalPlayerArea("You"), debugText));
        root.setLeft(createVerticalPlayerArea("Player 1"));
        root.setRight(createVerticalPlayerArea("Player 3"));

        discardPileView.setImage(CardLoader.loadCardImage(discardPileTop.getPossibleImageNames()[0]));
        drawPileView.setImage(CardLoader.loadCardImage("uno_0_card.png"));

        debugText.setScrollTop(Double.MAX_VALUE);
    }

    private HBox createHorizontalPlayerArea(String playerName) {
        HBox playerBox = new HBox(3);
        playerBox.setAlignment(Pos.CENTER);

        Text playerLabel = new Text(playerName + " (" + gameLogic.getPlayerHand(playerName).size() + " cards)");

        HBox cardBox = new HBox(2);
        for (Card card : gameLogic.getPlayerHand(playerName)) {
            cardBox.getChildren().add(createImageView(card));
        }

        VBox container = new VBox(2, playerLabel, cardBox);
        container.setAlignment(Pos.CENTER);
        playerBox.getChildren().add(container);
        return playerBox;
    }

    private void showColorSelection() {
        Stage colorStage = new Stage();
        colorStage.setTitle("Choose a Color");

        VBox colorBox = new VBox(10);
        colorBox.setAlignment(Pos.CENTER);

        Label label = new Label("Select a color for the Wild card:");

        Button redBtn = new Button("Red");
        Button blueBtn = new Button("Blue");
        Button greenBtn = new Button("Green");
        Button yellowBtn = new Button("Yellow");

        redBtn.setOnAction(e -> selectWildColor("red", colorStage));
        blueBtn.setOnAction(e -> selectWildColor("blue", colorStage));
        greenBtn.setOnAction(e -> selectWildColor("green", colorStage));
        yellowBtn.setOnAction(e -> selectWildColor("yellow", colorStage));

        colorBox.getChildren().addAll(label, redBtn, blueBtn, greenBtn, yellowBtn);

        Scene scene = new Scene(colorBox, 200, 150);
        colorStage.setScene(scene);
        colorStage.show();
    }

    private void selectWildColor(String color, Stage stage) {
        gameLogic.setWildCardColor(color);
        debugText.appendText("Wild card set to: " + color + "\n");
        lastMoveIndicator.setText("You chose color: " + color);
        stage.close();
        updateTurn();
    }


    private VBox createVerticalPlayerArea(String playerName) {
        VBox playerBox = new VBox(2);
        playerBox.setAlignment(Pos.CENTER);

        Text playerLabel = new Text(playerName + " (" + gameLogic.getPlayerHand(playerName).size() + " cards)");

        VBox cardBox = new VBox(2);
        for (Card card : gameLogic.getPlayerHand(playerName)) {
            cardBox.getChildren().add(createImageView(card));
        }

        playerBox.getChildren().addAll(playerLabel, cardBox);
        return playerBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
