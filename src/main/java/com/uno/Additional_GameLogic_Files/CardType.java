package com.uno.Additional_GameLogic_Files;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: CardType.java
 *
 * @Description: This class represents the card type.
 * */
public enum CardType {
    NUMBER,        // Regular numbered cards (0-9)
    SKIP,         // Skips the next player's turn
    REVERSE,      // Reverses the order of play
    DRAW_TWO,     // Forces the next player to draw two cards
    WILD_COLOR,   // Allows the player to choose the color
    WILD_DRAW_FOUR_CARDS // Allows the player to choose the color and forces the next player to draw four cards
}
