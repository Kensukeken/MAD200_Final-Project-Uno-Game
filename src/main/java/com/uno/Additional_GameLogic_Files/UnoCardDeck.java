package com.uno.Additional_GameLogic_Files;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: UnoCardDeck.java
 *
 * @Description: This class represents the uno card deck on the game.
 * */

/**
 * Represents a deck of UNO cards with 108 cards in total.
 */
public class UnoCardDeck {
    private final List<Card> deck = new ArrayList<>(108);

    public UnoCardDeck() {
        initializeDeck();
        shuffleDeck();
    }

    /**
     * Returns an unmodifiable list of the deck.
     *
     * @return an immutable list of UNO cards
     */
    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    /**
     * Populates the deck with UNO cards.
     */
    private void initializeDeck() {
        addNumberCards();
        addActionCards();
        addWildCards();
    }

    /**
     * Adds number cards (0-9) to the deck.
     * Each color has one 0 card and two of each 1-9 cards.
     */
    private void addNumberCards() {
        for (CardColor color : CardColor.values()) {
            if (color == CardColor.NONE) continue; // Skip NONE color since it's for wild cards
            deck.add(new Card(CardType.NUMBER, color, 0));

            for (int i = 1; i <= 9; i++) {
                deck.add(new Card(CardType.NUMBER, color, i));
                deck.add(new Card(CardType.NUMBER, color, i));
            }
        }
    }

    /**
     * Adds action cards (Skip, Reverse, Draw Two) to the deck.
     * Each color has two of each action card.
     */
    private void addActionCards() {
        for (CardColor color : CardColor.values()) {
            if (color == CardColor.NONE) continue; // Action cards must have a color
            for (int i = 0; i < 2; i++) {
                deck.add(new Card(CardType.SKIP, color, -1));
                deck.add(new Card(CardType.REVERSE, color, -1));
                deck.add(new Card(CardType.DRAW_TWO, color, -1));
            }
        }
    }

    /**
     * Adds wild cards (Wild Color and Wild Draw Four) to the deck.
     * There are four of each wild card.
     */
    private void addWildCards() {
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(CardType.WILD_COLOR, CardColor.NONE, -1));
            deck.add(new Card(CardType.WILD_DRAW_FOUR_CARDS, CardColor.NONE, -1));
        }
    }


    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public static void main(String[] args) {
        // Create a new UNO card deck
        UnoCardDeck deck = new UnoCardDeck();

        // Print total number of cards in the deck (should be 108)
        System.out.println("Total number of cards in the deck: " + deck.getDeck().size());

        // Print all cards in the deck
        for (Card card : deck.getDeck()) {
            System.out.println(card);
        }

        // Additional check: Count each type of card
        int numberCards = 0, actionCards = 0, wildCards = 0;

        for (Card card : deck.getDeck()) {
            if (card.getType() == CardType.NUMBER) {
                numberCards++;
            } else if (card.getType() == CardType.SKIP ||
                    card.getType() == CardType.REVERSE ||
                    card.getType() == CardType.DRAW_TWO) {
                actionCards++;
            } else if (card.getType() == CardType.WILD_COLOR ||
                    card.getType() == CardType.WILD_DRAW_FOUR_CARDS) {
                wildCards++;
            }
        }

        // Print the breakdown of card types
        System.out.println("\nBreakdown of cards:");
        System.out.println("Number cards: " + numberCards);
        System.out.println("Action cards (Skip, Reverse, Draw Two): " + actionCards);
        System.out.println("Wild cards (Wild Color, Wild Draw Four): " + wildCards);
    }
}
