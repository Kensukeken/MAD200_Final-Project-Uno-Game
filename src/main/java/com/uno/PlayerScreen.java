package com.uno;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
/**
 * @author: Hia Al Saleh, Yuanyang Chen, Mahta Haghbin
 * @date: February 20th to March 2nd 2025
 * @Filename: PlayerScreen.java
 *
 * @Description: This class represents the game logic with single player
 * */
public class PlayerScreen extends Application {
    private List<Card> playerHand;
    private Card discardPileTop;
    private ImageView discardPileView;
    private ImageView drawPileView;
    private GameLogic gameLogic;
    private BorderPane root;
    private Label turnIndicator;
    private Label lastMoveIndicator;
    private TextArea debugText;
    private Button backButton;

    @Override
    public void start(Stage stage) {
        initializeGameUI(stage);
    }

    private void initializeGameUI(Stage stage) {
        gameLogic = new GameLogic();
        gameLogic.initializeOneVsThreeComputerGame();

        playerHand = gameLogic.getCurrentPlayer().getHand();
        discardPileTop = gameLogic.getTopCard();

        root = new BorderPane();
        root.setStyle("-fx-background-color: #2c3e50;");

        // Main Top Container - This will hold both the control buttons and player area
        VBox mainTopContainer = new VBox();

        // Control Panel - For pause and back buttons
        HBox controlPanel = new HBox();
        controlPanel.setStyle("-fx-padding: 5; -fx-background-color: #34495e;");
        controlPanel.setAlignment(Pos.CENTER);

        // Back Button
        backButton = new Button("← Menu");
        backButton.setStyle("-fx-font-size: 12px; -fx-min-width: 70; -fx-background-color: #3498db; -fx-text-fill: white;");
        backButton.setOnAction(e -> returnToMainMenu(stage));

        // Game Info
        HBox gameInfo = new HBox(8);
        gameInfo.setAlignment(Pos.CENTER);

        turnIndicator = new Label("Turn: " + gameLogic.getCurrentPlayer().getName());
        turnIndicator.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");

        lastMoveIndicator = new Label("Last: None");
        lastMoveIndicator.setStyle("-fx-font-size: 12px; -fx-text-fill: #ecf0f1;");

        gameInfo.getChildren().addAll(turnIndicator, lastMoveIndicator);

        // Pause Button
        Button pauseButton = new Button("⏸ Pause");
        pauseButton.setStyle("-fx-font-size: 12px; -fx-min-width: 70; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        pauseButton.setOnAction(e -> showPauseMenu(stage));

        // Layout control panel with proper spacing
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        controlPanel.getChildren().addAll(backButton, leftSpacer, gameInfo, rightSpacer, pauseButton);

        mainTopContainer.getChildren().addAll(controlPanel, createHorizontalPlayerArea("Player 2"));
        root.setTop(mainTopContainer);

        //  Center Area - Card piles
        VBox centerArea = new VBox(10);
        centerArea.setAlignment(Pos.CENTER);

        // Piles
        HBox piles = new HBox(20);
        piles.setAlignment(Pos.CENTER);

        // Draw Pile
        VBox drawPile = new VBox(3);
        Text drawLabel = new Text("DRAW");
        drawLabel.setStyle("-fx-font-size: 12px; -fx-fill: white;");
        drawPileView = createHiddenDrawPileImage();
        drawPile.getChildren().addAll(drawLabel, drawPileView);

        // Discard Pile
        VBox discardPile = new VBox(3);
        Text discardLabel = new Text("PLAY");
        discardLabel.setStyle("-fx-font-size: 12px; -fx-fill: white;");
        discardPileView = createImageView(discardPileTop);
        discardPile.getChildren().addAll(discardLabel, discardPileView);

        piles.getChildren().addAll(drawPile, discardPile);
        centerArea.getChildren().add(piles);
        root.setCenter(centerArea);

        // Players area
        root.setRight(createVerticalPlayerArea("Player 3"));
        root.setLeft(createVerticalPlayerArea("Player 1"));
        root.setBottom(createPlayerAreaWithLog());

        Scene scene = new Scene(root, 950, 750);
        stage.setTitle("UNO Game");
        stage.setScene(scene);
        stage.show();
    }

    // Method to create player area with log
    private VBox createPlayerAreaWithLog() {
        VBox playerArea = new VBox(5);
        playerArea.setStyle("-fx-padding: 5;");

        // Your hand
        HBox yourHand = createHorizontalPlayerArea("You");
        yourHand.setStyle("-fx-background-color: rgba(52, 73, 94, 0.7); -fx-padding: 5;");

        // Debug log
        debugText = new TextArea();
        debugText.setEditable(false);
        debugText.setPrefHeight(70);
        debugText.setStyle("-fx-font-size: 11px; -fx-control-inner-background: #2c3e50; -fx-text-fill: white;");

        VBox logBox = new VBox(2, new Label("Game Log:"), debugText);
        logBox.setStyle("-fx-padding: 3;");

        playerArea.getChildren().addAll(yourHand, logBox);
        return playerArea;
    }

    // Method to create horizontal player area around the game
    private HBox createHorizontalPlayerArea(String playerName) {
        HBox area = new HBox(3);
        area.setAlignment(Pos.CENTER);

        Text label = new Text(playerName + " (" + gameLogic.getPlayerHand(playerName).size() + ")");
        label.setStyle("-fx-font-size: 12px; -fx-fill: white;");

        HBox cards = new HBox(2);
        for (Card card : gameLogic.getPlayerHand(playerName)) {
            ImageView view = createCompactCardView(card);
            cards.getChildren().add(view);
        }

        VBox container = new VBox(3, label, cards);
        container.setAlignment(Pos.CENTER);
        area.getChildren().add(container);
        return area;
    }

    // Method to create vertical player area around the game
    private VBox createVerticalPlayerArea(String playerName) {
        VBox area = new VBox(3);
        area.setAlignment(Pos.CENTER);
        area.setStyle("-fx-background-color: rgba(52, 73, 94, 0.7); -fx-padding: 5;");

        Text label = new Text(playerName + " (" + gameLogic.getPlayerHand(playerName).size() + ")");
        label.setStyle("-fx-font-size: 12px; -fx-fill: white;");

        VBox cards = new VBox(2);
        for (Card card : gameLogic.getPlayerHand(playerName)) {
            ImageView view = createCompactCardView(card);
            cards.getChildren().add(view);
        }

        area.getChildren().addAll(label, cards);
        return area;
    }

    // Method to create image view
    private ImageView createImageView(Card card) {
        ImageView view = new ImageView(CardLoader.loadCardImage(card.getPossibleImageNames()[0]));
        view.setFitWidth(50);
        view.setFitHeight(75);
        view.setPreserveRatio(true);
        view.setOnMouseClicked(e -> handleCardClick(card));
        return view;
    }

    // Method to create compact card view
    private ImageView createCompactCardView(Card card) {
        ImageView view = new ImageView(CardLoader.loadCardImage(card.getPossibleImageNames()[0]));
        view.setFitWidth(40);
        view.setFitHeight(60);
        view.setPreserveRatio(true);
        view.setOnMouseClicked(e -> handleCardClick(card));
        return view;
    }

    // Method to load the hidden draw pile card
    private ImageView createHiddenDrawPileImage() {
        ImageView view = new ImageView(CardLoader.loadCardImage("uno_0_card.png"));
        view.setFitWidth(50);
        view.setFitHeight(75);
        view.setPreserveRatio(true);
        view.setOnMouseClicked(e -> handleDrawCard());
        return view;
    }

    // Method to handle draw card
    private void handleDrawCard() {
        Card drawnCard = gameLogic.drawFromDeck(gameLogic.getCurrentPlayer());
        if (drawnCard != null) {
            playerHand.add(drawnCard);
            updateGameLog("You drew: " + drawnCard);
        } else {
            updateGameLog("Deck empty!");
        }
        refreshUI();
    }

    // Method to handle card click
    private void handleCardClick(Card card) {
        if (gameLogic.isValidMove(card, gameLogic.getTopCard())) {
            discardPileTop = card;
            playerHand.remove(card);
            updateGameLog("You played: " + card);

            if (card.getSuit().equals("wild")) {
                showColorSelection();
                return;
            }

            refreshUI();
            updateTurn();
        } else {
            showAlert("Invalid Move", "Can't play that card now!");
        }
    }

    // Method to show the color section of the game
    private void showColorSelection() {
        Stage colorStage = new Stage();
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 15; -fx-background-color: #34495e;");

        Label label = new Label("Choose Wild Color:");
        label.setStyle("-fx-text-fill: white;");

        HBox buttons = new HBox(8);
        buttons.setAlignment(Pos.CENTER);

        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        String[] colorCodes = {"#e74c3c", "#3498db", "#2ecc71", "#f1c40f"};

        for (int i = 0; i < colors.length; i++) {
            Button btn = new Button(colors[i]);
            btn.setStyle("-fx-font-size: 12px; -fx-background-color: " + colorCodes[i] + "; -fx-text-fill: white;");
            final String color = colors[i].toLowerCase();
            btn.setOnAction(e -> {
                gameLogic.setWildCardColor(color);
                updateGameLog("Wild: " + color);
                colorStage.close();
                updateTurn();
            });
            buttons.getChildren().add(btn);
        }

        box.getChildren().addAll(label, buttons);
        colorStage.setScene(new Scene(box, 250, 120));
        colorStage.show();
    }

    // This method represents the pause button of the game
    private void showPauseMenu(Stage mainStage) {
        Stage pauseStage = new Stage();
        VBox menu = new VBox(10);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-padding: 15; -fx-background-color: #34495e;");

        Label title = new Label("GAME PAUSED");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button resume = new Button("Resume");
        resume.setStyle("-fx-font-size: 12px; -fx-background-color: #2ecc71; -fx-text-fill: white;");
        resume.setOnAction(e -> pauseStage.close());

        Button mainMenu = new Button("Main Menu");
        mainMenu.setStyle("-fx-font-size: 12px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        mainMenu.setOnAction(e -> {
            pauseStage.close();
            returnToMainMenu(mainStage);
        });

        menu.getChildren().addAll(title, resume, mainMenu);
        pauseStage.setScene(new Scene(menu, 200, 150));
        pauseStage.show();
    }

    // Method to update the turns
    private void updateTurn() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            gameLogic.nextTurn();
            turnIndicator.setText("Turn: " + gameLogic.getCurrentPlayer().getName());

            if (!gameLogic.getCurrentPlayer().getName().equals("You")) {
                playAI();
            } else {
                refreshUI();
            }
        });
        delay.play();
    }

    // Playing with AI
    private void playAI() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            Card played = gameLogic.aiPlayCard();
            if (played != null) {
                discardPileTop = played;
                updateGameLog(gameLogic.getCurrentPlayer().getName() + " played: " + played);
            } else {
                updateGameLog(gameLogic.getCurrentPlayer().getName() + " drew card");
            }
            refreshUI();
            updateTurn();
        });
        delay.play();
    }

    // Method to refresh UI
    private void refreshUI() {
        discardPileView.setImage(CardLoader.loadCardImage(discardPileTop.getPossibleImageNames()[0]));
        root.setRight(createVerticalPlayerArea("Player 3"));
        root.setLeft(createVerticalPlayerArea("Player 1"));
        root.setBottom(createPlayerAreaWithLog());
        root.setTop(createHorizontalPlayerArea("Player 2"));
    }

    // Updates the game log
    private void updateGameLog(String message) {
        lastMoveIndicator.setText("Last: " + message.split("\n")[0]);
        debugText.appendText(message + "\n");
        debugText.setScrollTop(Double.MAX_VALUE);
    }

    // Method to shows alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to return to main menu
    private void returnToMainMenu(Stage currentStage) {
        try {
            currentStage.close();
            new MainMenu().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}