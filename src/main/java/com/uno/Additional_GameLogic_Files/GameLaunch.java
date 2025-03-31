package com.uno.Additional_GameLogic_Files;

import java.util.List;
import java.util.Scanner;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: GameLaunch.java
 *
 * @Description: This class represents the Game Launch whereas the game logic
 * */
public class GameLaunch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setup players: One human ("You") and three AIs
        Player humanPlayer = new Player("You", false);
        Player ai1 = new Player("AI-1", true);
        Player ai2 = new Player("AI-2", true);
        Player ai3 = new Player("AI-3", true);

        List<Player> players = List.of(humanPlayer, ai1, ai2, ai3);
        List<Card> deck = new UnoCardDeck().getDeck();

        // Start the game session
        GameSession gameSession = new GameSession(players, deck);

        System.out.println("Game has started!");
        System.out.println("You are playing against AI-1, AI-2, and AI-3.");

        while (!gameSession.isGameOver()) {
            Player currentPlayer = gameSession.getCurrentPlayer();
            System.out.println("Current turn: " + currentPlayer.getName());

            if (currentPlayer.isAI()) {
                // AI automatically plays or draws
                List<Card> handCards = currentPlayer.getPlayerCardList().getCardList();
                boolean played = false;

                for (Card card : handCards) {
                    if (gameSession.playCard(currentPlayer, card)) {
                        played = true;
                        break;
                    }
                }

                if (!played) {
                    System.out.println(currentPlayer.getName() + " has no valid cards. Drawing...");
                    gameSession.drawCard(currentPlayer);
                }
            } else {
                // Human player's turn
                System.out.println("Your hand: " + humanPlayer.getPlayerCardList().getCardList());
                System.out.println("Enter the index of the card you want to play, or type 'draw' to draw a card:");

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("draw")) {
                    gameSession.drawCard(humanPlayer);
                } else {
                    try {
                        int cardIndex = Integer.parseInt(input);
                        if (cardIndex >= 0 && cardIndex < humanPlayer.getPlayerCardList().getCardList().size()) {
                            Card chosenCard = humanPlayer.getPlayerCardList().getCardList().get(cardIndex);
                            gameSession.playCard(humanPlayer, chosenCard);
                        } else {
                            System.out.println("Invalid selection. Try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Try again.");
                    }
                }
            }
        }

        System.out.println("Game Over! Winner: " + gameSession.getWinner().getName());
        scanner.close();
    }
}
