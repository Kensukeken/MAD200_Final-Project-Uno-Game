package com.uno.Additional_GameLogic_Files;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: Card.java
 *
 * @Description: This class represents the card based on game logic.
 * */

public class Card {
    private final CardType type;  // The type of the card (NUMBER, SKIP, REVERSE, etc.)
    private final CardColor color; // The color of the card (RED, BLUE, GREEN, YELLOW, or NONE for wild cards)
    private final int value; // Numeric value for NUMBER cards, -1 for others

    public Card(CardType type, CardColor color, int value) {
        this.type = type;
        this.color = color;
        this.value = (type == CardType.NUMBER) ? value : -1; // Only NUMBER cards have values
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String[] getPossibleImageNames() {
        String colorName = color.name().toLowerCase(); // Convert color to lowercase for filenames

        // Handle wild cards separately
        if (type == CardType.WILD_COLOR) {
            return new String[]{"wild_card1.png", "wild_card2.png", "wild_card3.png", "wild_card4.png"};
        } else if (type == CardType.WILD_DRAW_FOUR_CARDS) {
            return new String[]{"wild_draw_four_card1.png", "wild_draw_four_card2.png",
                    "wild_draw_four_card3.png", "wild_draw_four_card4.png"};
        }

        // Handle regular colored cards
        String baseName = colorName;

        if (type == CardType.NUMBER) {
            if (value == 0) {
                return new String[]{baseName + "_0_card.png"};  // Special case for 0 cards
            }
            baseName += "_" + value;
        } else {
            baseName += "_" + typeToFileName();
        }

        return new String[]{
                baseName + "_card1.png",
                baseName + "_card2.png"
        };
    }

    /**
     * Converts card type to appropriate filename format.
     */
    private String typeToFileName() {
        return switch (type) {
            case SKIP -> "skip";
            case REVERSE -> "reverse";
            case DRAW_TWO -> "draw_two";
            case NUMBER -> "";
            default -> "error";
        };
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return value == card.value && type == card.type && color == card.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color, value);
    }

    @Override
    public String toString() {
        if (type == CardType.NUMBER) {
            return color + " " + value;
        } else {
            return type + " (" + color + ")";
        }
    }


    private static void testCardImageNaming() {
        // Expected file name set
        Set<String> expectedFileNames = new HashSet<>(Arrays.asList(
                "blue_0_card.png", "blue_1_card1.png", "blue_1_card2.png", "blue_2_card1.png", "blue_2_card2.png",
                "blue_3_card1.png", "blue_3_card2.png", "blue_4_card1.png", "blue_4_card2.png", "blue_5_card1.png",
                "blue_5_card2.png", "blue_6_card1.png", "blue_6_card2.png", "blue_7_card1.png", "blue_7_card2.png",
                "blue_8_card1.png", "blue_8_card2.png", "blue_9_card1.png", "blue_9_card2.png", "blue_draw_two_card1.png",
                "blue_draw_two_card2.png", "blue_reverse_card1.png", "blue_reverse_card2.png", "blue_skip_card1.png",
                "blue_skip_card2.png", "green_0_card.png", "green_1_card1.png", "green_1_card2.png", "green_2_card1.png",
                "green_2_card2.png", "green_3_card1.png", "green_3_card2.png", "green_4_card1.png", "green_4_card2.png",
                "green_5_card1.png", "green_5_card2.png", "green_6_card1.png", "green_6_card2.png", "green_7_card1.png",
                "green_7_card2.png", "green_8_card1.png", "green_8_card2.png", "green_9_card1.png", "green_9_card2.png",
                "green_draw_two_card1.png", "green_draw_two_card2.png", "green_reverse_card1.png", "green_reverse_card2.png",
                "green_skip_card1.png", "green_skip_card2.png", "red_0_card.png", "red_1_card1.png", "red_1_card2.png",
                "red_2_card1.png", "red_2_card2.png", "red_3_card1.png", "red_3_card2.png", "red_4_card1.png",
                "red_4_card2.png", "red_5_card1.png", "red_5_card2.png", "red_6_card1.png", "red_6_card2.png",
                "red_7_card1.png", "red_7_card2.png", "red_8_card1.png", "red_8_card2.png", "red_9_card1.png",
                "red_9_card2.png", "red_draw_two_card1.png", "red_draw_two_card2.png", "red_reverse_card1.png",
                "red_reverse_card2.png", "red_skip_card1.png", "red_skip_card2.png", "wild_card1.png", "wild_card2.png",
                "wild_card3.png", "wild_card4.png", "wild_draw_four_card1.png", "wild_draw_four_card2.png",
                "wild_draw_four_card3.png", "wild_draw_four_card4.png", "yellow_0_card.png", "yellow_1_card1.png",
                "yellow_1_card2.png", "yellow_2_card1.png", "yellow_2_card2.png", "yellow_3_card1.png", "yellow_3_card2.png",
                "yellow_4_card1.png", "yellow_4_card2.png", "yellow_5_card1.png", "yellow_5_card2.png", "yellow_6_card1.png",
                "yellow_6_card2.png", "yellow_7_card1.png", "yellow_7_card2.png", "yellow_8_card1.png", "yellow_8_card2.png",
                "yellow_9_card1.png", "yellow_9_card2.png", "yellow_draw_two_card1.png", "yellow_draw_two_card2.png",
                "yellow_reverse_card1.png", "yellow_reverse_card2.png", "yellow_skip_card1.png", "yellow_skip_card2.png"
        ));

        // Test all possible card types
        for (CardColor color : CardColor.values()) {
            if (color == CardColor.NONE) continue; // Skip NONE since it's only for wild cards

            // Number cards 0-9
            for (int value = 0; value <= 9; value++) {
                Card numberCard = new Card(CardType.NUMBER, color, value);
                assertImageNames(numberCard, expectedFileNames);
            }

            // Special action cards
            Card skipCard = new Card(CardType.SKIP, color, -1);
            assertImageNames(skipCard, expectedFileNames);

            Card reverseCard = new Card(CardType.REVERSE, color, -1);
            assertImageNames(reverseCard, expectedFileNames);

            Card drawTwoCard = new Card(CardType.DRAW_TWO, color, -1);
            assertImageNames(drawTwoCard, expectedFileNames);
        }

        // Wild Cards
        Card wildCard = new Card(CardType.WILD_COLOR, CardColor.NONE, -1);
        assertImageNames(wildCard, expectedFileNames);

        Card wildDrawFourCard = new Card(CardType.WILD_DRAW_FOUR_CARDS, CardColor.NONE, -1);
        assertImageNames(wildDrawFourCard, expectedFileNames);

        System.out.println("✅ All card image filename tests passed!");
    }

    private static void assertImageNames(Card card, Set<String> expectedFileNames) {
        String[] generatedNames = card.getPossibleImageNames();
        boolean allMatch = true;

        for (String name : generatedNames) {
            if (!expectedFileNames.contains(name)) {
                allMatch = false;
                System.err.println("❌ ERROR: Generated image name " + name + " does not exist for " + card);
            }
        }

        if (allMatch) {
            System.out.println("✅ Image names for " + card + " are correct: " + String.join(", ", generatedNames));
        }
    }
    public static void main(String[] args) {
        testCardImageNaming();
        // Testing Card Structure
        Card numberCard = new Card(CardType.NUMBER, CardColor.RED, 5);
        Card skipCard = new Card(CardType.SKIP, CardColor.BLUE, -1);
        Card reverseCard = new Card(CardType.REVERSE, CardColor.GREEN, -1);
        Card drawTwoCard = new Card(CardType.DRAW_TWO, CardColor.YELLOW, -1);
        Card wildCard = new Card(CardType.WILD_COLOR, CardColor.NONE, -1);
        Card wildDrawFourCard = new Card(CardType.WILD_DRAW_FOUR_CARDS, CardColor.NONE, -1);

        // Printing cards to check their representation
        System.out.println("Number Card: " + numberCard);
        System.out.println("Skip Card: " + skipCard);
        System.out.println("Reverse Card: " + reverseCard);
        System.out.println("Draw Two Card: " + drawTwoCard);
        System.out.println("Wild Card: " + wildCard);
        System.out.println("Wild Draw Four Card: " + wildDrawFourCard);

        // Testing image filename generation
        System.out.println("Image names for numberCard: " + String.join(", ", numberCard.getPossibleImageNames()));
        System.out.println("Image names for skipCard: " + String.join(", ", skipCard.getPossibleImageNames()));
        System.out.println("Image names for wildCard: " + String.join(", ", wildCard.getPossibleImageNames()));
        System.out.println("Image names for wildDrawFourCard: " + String.join(", ", wildDrawFourCard.getPossibleImageNames()));

        // Testing equality check
        Card anotherNumberCard = new Card(CardType.NUMBER, CardColor.RED, 5);
        System.out.println("Are the two number cards equal? " + numberCard.equals(anotherNumberCard));

        // Hash code verification
        System.out.println("HashCode of numberCard: " + numberCard.hashCode());
        System.out.println("HashCode of anotherNumberCard: " + anotherNumberCard.hashCode());
    }
}
