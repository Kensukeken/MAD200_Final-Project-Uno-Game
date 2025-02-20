package com.uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a deck of cards in the game
public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Red", "Green", "Blue", "Yellow"};
        String[] ranks = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Reverse", "Draw Two"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
                if (!rank.equals("0")) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public void deal(List<Player> players, int cardsPerPlayer) {
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (Player player : players) {
                if (!cards.isEmpty()) {
                    player.receiveCard(drawCard());
                }
            }
        }
    }
}
