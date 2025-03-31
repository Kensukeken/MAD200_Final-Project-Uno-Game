package com.uno.Additional_GameLogic_Files;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: SinglePlayerScreen.java
 *
 * @Description: This is just an improved single player screen with more additional game logic files.
 * The real one is PlayerScreen.java
 * */

public class SinglePlayerScreen extends Application implements GameEventPublisher.GameEventListener {
    private GameSession gameSession;
    private Player humanPlayer;
    private BorderPane root;
    private Label turnIndicator;
    private Label lastMoveIndicator;
    private TextArea debugText;
    private ImageView discardPileView;
    private ImageView drawPileView;

    @Override
    public void start(Stage stage) {
        initializeGame();

        root = new BorderPane();
        root.setMinSize(700, 500);

        turnIndicator = new Label("Turn: " + gameSession.getCurrentPlayer().getName());
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
        discardPileView = createImageView(gameSession.getTopCard());

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

        // Subscribe to GameEventPublisher
        GameEventPublisher.subscribe(this);
    }

    private void initializeGame() {
        Player player1 = new Player("You", false);
        Player player2 = new Player("AI-1", true);
        Player player3 = new Player("AI-2", true);
        Player player4 = new Player("AI-3", true);

        List<Player> players = List.of(player1, player2, player3, player4);
        List<Card> deck = new UnoCardDeck().getDeck();

        humanPlayer = player1;
        gameSession = new GameSession(players, deck);
    }

    private ImageView createImageView(Card card) {
        if (card == null) return new ImageView();
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
            gameSession.drawCard(humanPlayer);
        });

        return drawPileView;
    }

    private void handleCardClick(Card clickedCard) {
        if (!gameSession.playCard(humanPlayer, clickedCard)) {
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
            gameSession.getCurrentPlayer();
            refreshUI();

            if (!gameSession.getCurrentPlayer().getName().equals("You")) {
                playAI();
            }
        });
        delay.play();
    }

    private void playAI() {
        PauseTransition aiDelay = new PauseTransition(Duration.seconds(1));
        aiDelay.setOnFinished(ev -> {
            gameSession.playGame();
        });
        aiDelay.play();
    }

    private void refreshUI() {
        root.setTop(createHorizontalPlayerArea("Player 2"));
        root.setBottom(new VBox(5, createHorizontalPlayerArea("You"), debugText));
        root.setLeft(createVerticalPlayerArea("Player 1"));
        root.setRight(createVerticalPlayerArea("Player 3"));

        discardPileView.setImage(CardLoader.loadCardImage(gameSession.getTopCard().getPossibleImageNames()[0]));
        drawPileView.setImage(CardLoader.loadCardImage("uno_0_card.png"));

        debugText.setScrollTop(Double.MAX_VALUE);
    }

    private HBox createHorizontalPlayerArea(String playerName) {
        HBox playerBox = new HBox(3);
        playerBox.setAlignment(Pos.CENTER);

        Text playerLabel = new Text(playerName + " (" + gameSession.getCurrentPlayer().getPlayerCardList().size() + " cards)");

        HBox cardBox = new HBox(2);
        for (Card card : gameSession.getCurrentPlayer().getPlayerCardList().getCardList()) {
            cardBox.getChildren().add(createImageView(card));
        }

        VBox container = new VBox(2, playerLabel, cardBox);
        container.setAlignment(Pos.CENTER);
        playerBox.getChildren().add(container);
        return playerBox;
    }

    private VBox createVerticalPlayerArea(String playerName) {
        VBox playerBox = new VBox(2);
        playerBox.setAlignment(Pos.CENTER);

        Text playerLabel = new Text(playerName + " (" + gameSession.getCurrentPlayer().getPlayerCardList().size() + " cards)");

        VBox cardBox = new VBox(2);
        for (Card card : gameSession.getCurrentPlayer().getPlayerCardList().getCardList()) {
            cardBox.getChildren().add(createImageView(card));
        }

        playerBox.getChildren().addAll(playerLabel, cardBox);
        return playerBox;
    }

    @Override
    public void onEvent(GameEventPublisher.GameEvent event) {
        Platform.runLater(() -> {
            if (event instanceof GameEventPublisher.CardPlayedEvent e) {
                lastMoveIndicator.setText(e.player.getName() + " played " + e.card);
                debugText.appendText(e.player.getName() + " played " + e.card + "\n");
                refreshUI();
            } else if (event instanceof GameEventPublisher.CardDrawnEvent e) {
                lastMoveIndicator.setText(e.player.getName() + " drew a card.");
                debugText.appendText(e.player.getName() + " drew a card.\n");
                refreshUI();
            } else if (event instanceof GameEventPublisher.TurnChangedEvent e) {
                turnIndicator.setText("Turn: " + e.newPlayer.getName());
                debugText.appendText("Turn changed to " + e.newPlayer.getName() + "\n");
                refreshUI();
            } else if (event instanceof GameEventPublisher.GameOverEvent e) {
                turnIndicator.setText("Game Over! Winner: " + e.winner.getName());
                debugText.appendText("Game Over! Winner: " + e.winner.getName() + "\n");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
