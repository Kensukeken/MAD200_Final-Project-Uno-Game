package com.uno.Additional_GameLogic_Files;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: PileManager.java
 *
 * @Description: A class represents the pile manager.
 * */

public class PileManager {
    private final Stack<Card> drawPile;
    private final Stack<Card> discardPile;

    public PileManager(List<Card> initialCards) {
        this.drawPile = new Stack<>();
        this.discardPile = new Stack<>();
        initializeDrawPile(initialCards);
    }

    /**
     * Initializes the draw pile with shuffled cards.
     */
    private void initializeDrawPile(List<Card> cards) {
        List<Card> shuffledCards = new ArrayList<>(cards); // Copy original cards
        Collections.shuffle(shuffledCards); // Shuffle the copy
        drawPile.addAll(shuffledCards); // Add shuffled cards to draw pile
    }

    /**
     * Draws a card from the draw pile.
     * If the draw pile is empty, it attempts to recreate it from the discard pile.
     *
     * @return The drawn card, or null if no cards are available.
     */
    public Card drawCard() {
        if (drawPile.isEmpty()) {
            recreateDrawPile();
        }
        return drawPile.isEmpty() ? null : drawPile.pop();
    }

    /**
     * Discards a card onto the discard pile.
     *
     * @param card The card to be discarded.
     */
    public void discardCard(Card card) {
        discardPile.push(card);
    }

    /**
     * Peeks at the top card of the discard pile without removing it.
     *
     * @return The top card of the discard pile, or null if the discard pile is empty.
     */
    public Card peekTopDiscard() {
        return discardPile.isEmpty() ? null : discardPile.peek();
    }

    /**
     * Refills the draw pile from the discard pile if the draw pile is empty.
     * The last played card remains on top of the discard pile.
     */
    private void recreateDrawPile() {
        if (discardPile.isEmpty()) {
            System.out.println("No cards left in discard pile to refill draw pile.");
            return;
        }

        System.out.println("Draw pile is empty! Refilling from discard pile...");

        Card lastPlayedCard = discardPile.pop();  // Keep last played card
        List<Card> shuffledCards = new ArrayList<>(discardPile);
        Collections.shuffle(shuffledCards); // Shuffle before adding back

        drawPile.addAll(shuffledCards);
        discardPile.clear();
        discardPile.push(lastPlayedCard); // Keep the last played card on top

        System.out.println("Draw pile refilled and shuffled. New draw pile size: " + drawPile.size());
    }

    /**
     * Returns the number of cards left in the draw pile.
     *
     * @return The size of the draw pile.
     */
    public int getDrawPileSize() {
        return drawPile.size();
    }

    /**
     * Returns the number of cards in the discard pile.
     *
     * @return The size of the discard pile.
     */
    public int getDiscardPileSize() {
        return discardPile.size();
    }

    /**
     * Generates initial hand cards for all players at the start of the game.
     *
     * @param handSize   The number of cards each player should start with.
     * @param playerCount The total number of players.
     * @return A list of PlayerCardList representing each player's hand.
     */
    public List<PlayerCardList> generateHandCards(int handSize, int playerCount) {
        List<PlayerCardList> hands = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            hands.add(new PlayerCardList());
        }

        for (int i = 0; i < handSize; i++) {
            for (PlayerCardList hand : hands) {
                if (!drawPile.isEmpty()) {
                    hand.addCard(drawCard());
                }
            }
        }
        return hands;
    }

    /**
     * Debugging method to print the current draw pile.
     */
    public void printDrawPile() {
        System.out.println("\nDraw Pile: " + drawPile);
    }

    /**
     * Debugging method to print the discard pile.
     */
    public void printDiscardPile() {
        System.out.println("\nDiscard Pile: " + discardPile);
    }

    /**
     * Debugging method to print both piles.
     */
    public void printPiles() {
        printDrawPile();
        printDiscardPile();
    }

    public static void main(String[] args) {
        List<Card> initialCards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            initialCards.add(new Card(CardType.NUMBER, CardColor.RED, i));
        }
        for (int i = 1; i <= 5; i++) {
            initialCards.add(new Card(CardType.SKIP, CardColor.BLUE, -1));
        }

        System.out.println("Initializing PileManager...");
        PileManager pileManager = new PileManager(initialCards);
        pileManager.printPiles();

        System.out.println("\nDrawing cards...");
        while (pileManager.getDrawPileSize() > 0) {
            Card drawnCard = pileManager.drawCard();
            System.out.println("Drew card: " + drawnCard);
        }

        System.out.println("\nDraw pile size after drawing: " + pileManager.getDrawPileSize());
        System.out.println("Discarding some cards...");

        for (int i = 1; i <= 5; i++) {
            Card card = new Card(CardType.NUMBER, CardColor.YELLOW, i);
            pileManager.discardCard(card);
            System.out.println("Discarded card: " + card);
        }

        System.out.println("\nTop discard pile card: " + pileManager.peekTopDiscard());

        System.out.println("\nManually triggering draw pile recreation...");
        pileManager.recreateDrawPile();
        pileManager.printPiles();

        Card newDraw = pileManager.drawCard();
        System.out.println("\nAfter recreation, drew: " + (newDraw != null ? newDraw : "No card left to draw"));

        System.out.println("\nGenerating hand cards...");
        List<PlayerCardList> playerHands = pileManager.generateHandCards(5, 3);

        for (int i = 0; i < playerHands.size(); i++) {
            System.out.println("Player " + (i + 1) + "'s hand: " + playerHands.get(i).getCardList());
        }
    }
}
