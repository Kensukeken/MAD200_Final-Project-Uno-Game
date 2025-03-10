package com.uno;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a deck of UNO cards
public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"red", "green", "blue", "yellow"};
        String[] ranks = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "skip", "reverse", "draw two"};

        // Add regular colored cards (0-9, Skip, Reverse, Draw Two)
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
                if (!rank.equals("0")) { // Each non-zero card has two copies
                    cards.add(new Card(suit, rank));
                }
            }
        }

        // Add 4 Wild cards and 4 Wild Draw Four cards
        for (int i = 1; i <= 4; i++) {
            cards.add(new Card("wild", "wild"));
            cards.add(new Card("wild", "wild draw four"));
        }

        shuffle();
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Draw a card from the deck
    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty! No more cards to draw.");
            return null;
        }
        return cards.remove(0);
    }

    // Deal cards to players
    public void deal(List<Player> players, int cardsPerPlayer) {
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (Player player : players) {
                if (!cards.isEmpty()) {
                    player.receiveCard(drawCard());
                }
            }
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }
}
