package com.uno;

import java.util.List;

// Represents the game
public class Game {
    private final List<Player> players;
    private int currentPlayerIndex;

    public Game(List<Player> players) {
        this.players = players;
        Deck deck = new Deck();
        this.currentPlayerIndex = 0;
        deck.shuffle();
        deck.deal(players, 7);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void displayGameState() {
        System.out.println("Current turn: " + getCurrentPlayer().getName());
    }
}
