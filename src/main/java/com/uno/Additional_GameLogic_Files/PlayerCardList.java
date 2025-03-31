package com.uno.Additional_GameLogic_Files;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: PlayerCardList.java
 *
 * @Description: This class represents the player card list on the game.
 * */

public class PlayerCardList {
    private final List<Card> handCards; // List of cards in hand

    public PlayerCardList() {
        this.handCards = new ArrayList<>();
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        handCards.add(card);
    }

    // Remove a card from the player's hand
    public boolean removeCard(Card card) {
        return handCards.remove(card);
    }

    // Check if the player has a specific card
    public boolean hasCard(Card card) {
        return handCards.contains(card);
    }

    // Get the number of cards in hand
    public int size() {
        return handCards.size();
    }

    // Get the list of cards (for Player.showCards())
    public List<Card> getCardList() {
        return new ArrayList<>(handCards); // Return a copy to prevent modification
    }

    // Show all cards in hand
    @Override
    public String toString() {
        return handCards.toString();
    }
}
