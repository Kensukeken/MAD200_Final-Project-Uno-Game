package com.uno;
/**
 * @author: Yuanyang Chen
 * @date: March 2nd, 2025
 * @Filename: Card.java
 *
 * @Description: This represents the card for the game logic.
 * */

// Represents a card in the UNO game
public class Card {
    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit.toLowerCase(); // Normalize for consistent lookup
        this.rank = rank.toLowerCase();
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    /**
     * Generates possible file names for loading card images.
     * @return An array of possible image file names.
     */
    public String[] getPossibleImageNames() {
        // Handle wild cards separately
        if (suit.equals("wild")) {
            if (rank.equals("wild draw four")) {
                return new String[]{"wild_draw_four_card1.png", "wild_draw_four_card2.png",
                        "wild_draw_four_card3.png", "wild_draw_four_card4.png"};
            }
            return new String[]{"wild_card1.png", "wild_card2.png", "wild_card3.png", "wild_card4.png"};
        }

        // Handle regular colored cards
        String baseName = suit + "_" + rank.replace(" ", "_"); // Ensure proper filename format

        // Only add filenames that actually exist in your resources
        if (rank.equals("0")) {
            return new String[]{baseName + "_card.png"}; // Zero cards only have one image
        }

        return new String[]{
                baseName + "_card1.png",
                baseName + "_card2.png"
        };
    }



    @Override
    public String toString() {
        if (suit.equalsIgnoreCase("wild")) {
            return rank; // Wild cards only need rank
        }
        return suit.substring(0, 1).toUpperCase() + suit.substring(1) + " " + rank; // Ensure proper capitalization
    }
}
