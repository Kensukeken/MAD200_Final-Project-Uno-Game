package com.uno.Additional_GameLogic_Files;

import java.util.List;
import java.util.UUID;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename:  Player.java
 *
 * @Description: This class represents the player on the game logic
 * */

public class Player {
    private final UUID playerId;  // Unique player ID
    private final String playerName; // Player name
    private final PlayerCardList playerCardList; // Holds the player's cards
    private final boolean isAI; // Flag to check if the player is an AI

    // Constructor for human player
    public Player(String name) {
        this(name, false); // Default to human player
    }

    // Constructor for AI player
    public Player(String name, boolean isAI) {
        this.playerId = UUID.randomUUID();
        this.playerName = name;
        this.playerCardList = new PlayerCardList();
        this.isAI = isAI;
    }

    // Get player's ID
    public UUID getId() {
        return playerId;
    }

    // Get player's name
    public String getName() {
        return playerName;
    }

    // Check if this is an AI player
    public boolean isAI() {
        return isAI;
    }

    // Show player's cards as a list
    public List<Card> showCards() {
        return playerCardList.getCardList();
    }

    // Add a card to hand
    public void addCard(Card card) {
        playerCardList.addCard(card);
    }

    // Remove a played card
    public boolean discardCard(Card card) {
        return playerCardList.removeCard(card);
    }

    // Check if player has a card
    public boolean ownsCard(Card card) {
        return playerCardList.hasCard(card);
    }

    // Get number of cards in hand
    public int countCards() {
        return playerCardList.size();
    }

    public PlayerCardList getPlayerCardList() {
        return playerCardList;
    }

    // Player info
    @Override
    public String toString() {
        return playerName + " (Cards: " + countCards() + ")";
    }

    public static void main(String[] args) {
        // The Test for Player
        // Create a player
        Player player = new Player("Yuanyang");

        // Create some UNO cards
        Card redFive = new Card(CardType.NUMBER, CardColor.RED, 5);
        Card blueSkip = new Card(CardType.SKIP, CardColor.BLUE, -1);
        Card yellowDrawTwo = new Card(CardType.DRAW_TWO, CardColor.YELLOW, -1);
        Card wildCard = new Card(CardType.WILD_COLOR, CardColor.NONE, -1);

        // Add cards to player's hand
        player.addCard(redFive);
        player.addCard(blueSkip);
        player.addCard(yellowDrawTwo);
        player.addCard(wildCard);

        // Display player's hand
        System.out.println(player.getName() + "'s cards: " + player.showCards());

        // Check if player has a specific card
        System.out.println("Does Yuanyang have RED 5? " + player.ownsCard(redFive));
        System.out.println("Does Yuanyang have GREEN 3? " + player.ownsCard(new Card(CardType.NUMBER, CardColor.GREEN, 3)));

        // Remove a card
        boolean removed = player.discardCard(blueSkip);
        System.out.println("After playing SKIP (BLUE), remaining cards: " + player.showCards());
        System.out.println("Card removed: " + removed);

        // Show the number of cards left
        System.out.println(player.getName() + " now has " + player.countCards() + " cards.");

        // Test AI player
        Player aiPlayer = new Player("AI_Player", true);
        System.out.println(aiPlayer.getName() + " is AI: " + aiPlayer.isAI());
    }
}
