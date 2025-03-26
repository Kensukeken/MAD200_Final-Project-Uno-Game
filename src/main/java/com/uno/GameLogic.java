package com.uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: Yuanyang Chen, Mahta Haghbin
 * @date: March 2nd, 2025
 * @Filename: GameLogic.java
 *
 * @Description: This is the main class for the game logic on the project.
 * */
public class GameLogic {
    private List<Player> players;
    private int currentPlayerIndex;
    private List<Card> discardPile;
    private Deck deck;
    private Random random;
    private String currentWildColor;

    public GameLogic() {
        initializeOneVsThreeComputerGame();
    }

    public void initializeOneVsThreeComputerGame() {
        players = new ArrayList<>();
        players.add(new Player("You"));
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        players.add(new Player("Player 3"));

        currentPlayerIndex = 0;
        deck = new Deck();
        discardPile = new ArrayList<>();
        random = new Random();
        currentWildColor = null;

        deck.shuffle();
        deck.deal(players, 7);

        discardPile.add(getRandomCard());
        debugGameState("Game started (1 vs 3 Mode)");
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Card> getPlayerHand(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player.getHand();
            }
        }
        return new ArrayList<>();
    }

    public Card getTopCard() {
        return discardPile.isEmpty() ? getRandomCard() : discardPile.get(discardPile.size() - 1);
    }

    private Card getRandomCard() {
        return deck.isEmpty() ? null : deck.drawCard();
    }

    public boolean isValidMove(Card playedCard, Card topCard) {
        if (playedCard == null || topCard == null) {
            return false;
        }

        if (playedCard.getSuit().equals("wild")) {
            return true;
        }

        if (topCard.getSuit().equals("wild") && currentWildColor != null) {
            return playedCard.getSuit().equals(currentWildColor);
        }

        return playedCard.getSuit().equals(topCard.getSuit()) ||
                playedCard.getRank().equals(topCard.getRank());
    }

    public void setWildCardColor(String color) {
        currentWildColor = color;
        System.out.println(" Wild card color set to: " + color);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        debugGameState("Turn moved to: " + getCurrentPlayer().getName());

        selfCheckGameLogic();

        if (!getCurrentPlayer().getName().equals("You")) {
            aiPlayCard();
        }
    }

    public Card aiPlayCard() {
        Player aiPlayer = getCurrentPlayer();
        if (aiPlayer.getName().equals("You")) {
            return null;
        }

        Card topCard = getTopCard();
        for (Card card : aiPlayer.getHand()) {
            if (isValidMove(card, topCard)) {
                discardPile.add(card);
                aiPlayer.playCard(card);
                debugGameState(aiPlayer.getName() + " played: " + card);

                if (card.getSuit().equals("wild")) {
                    String[] colors = {"red", "blue", "green", "yellow"};
                    String chosenColor = colors[random.nextInt(colors.length)];
                    setWildCardColor(chosenColor);
                }

                if (card.getRank().equals("Draw Two")) {
                    forceNextPlayerDraw(2);
                } else {
                    nextTurn();
                }
                return card;
            }
        }

        Card drawnCard = drawFromDeck(aiPlayer);
        if (drawnCard != null && isValidMove(drawnCard, topCard)) {
            discardPile.add(drawnCard);
            aiPlayer.playCard(drawnCard);
            debugGameState(aiPlayer.getName() + " drew and played: " + drawnCard);
            nextTurn();
            return drawnCard;
        }

        debugGameState(aiPlayer.getName() + " drew a card but couldn't play.");
        nextTurn();
        return null;
    }

    private void forceNextPlayerDraw(int count) {
        int nextIndex = (currentPlayerIndex + 1) % players.size();
        Player nextPlayer = players.get(nextIndex);

        for (int i = 0; i < count; i++) {
            Card drawnCard = drawFromDeck(nextPlayer);
            if (drawnCard == null) break;
        }

        debugGameState(nextPlayer.getName() + " was forced to draw " + count + " cards.");
        currentPlayerIndex = nextIndex;
        nextTurn();
    }

    public Card drawFromDeck(Player player) {
        if (deck.isEmpty()) {
            debugGameState("Deck is empty! No card can be drawn.");
            return null;
        }

        Card drawnCard = deck.drawCard();
        player.receiveCard(drawnCard);
        debugGameState(player.getName() + " drew a card: " + drawnCard);
        return drawnCard;
    }

    public void selfCheckGameLogic() {
        System.out.println("===== GAME SELF-CHECK START =====");

        int totalCardsInPlay = 0;
        for (Player player : players) {
            totalCardsInPlay += player.getHand().size();
        }
        totalCardsInPlay += discardPile.size() + deck.size();

        if (totalCardsInPlay != 108) {
            System.out.println("WARNING: Total cards in play is " + totalCardsInPlay + " instead of 108!");
        } else {
            System.out.println(" Total cards count is correct: " + totalCardsInPlay);
        }

        for (Player player : players) {
            if (player.getHand().size() < 0) {
                System.out.println("ERROR: " + player.getName() + " has negative cards!");
            }
            if (player.getHand().isEmpty() && !player.getName().equals("You")) {
                System.out.println("WIN CONDITION: " + player.getName() + " has won the game!");
            }
            System.out.println(" " + player.getName() + " has " + player.getHand().size() + " cards.");
        }

        if (deck.size() < 0) {
            System.out.println("ERROR: Deck size is negative! " + deck.size());
        } else {
            System.out.println(" Deck has " + deck.size() + " cards remaining.");
        }

        if (discardPile.isEmpty()) {
            System.out.println("ERROR: Discard pile is empty!");
        } else {
            System.out.println(" Discard pile top card: " + getTopCard());
        }

        if (getTopCard().getSuit().equals("wild") && currentWildColor == null) {
            System.out.println("ERROR: Wild card played but no color set!");
        }

        System.out.println(" Current Turn: " + getCurrentPlayer().getName());
        int nextTurnIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println(" Next Turn: " + players.get(nextTurnIndex).getName());

        System.out.println("===== GAME SELF-CHECK COMPLETE =====");
    }

    private void debugGameState(String message) {
        System.out.println("=== GAME STATE UPDATE ===");
        System.out.println(message);
        System.out.println("Current Turn: " + getCurrentPlayer().getName());
        System.out.println("Discard Pile Top: " + getTopCard());
        System.out.println("Current Wild Card Color: " + (currentWildColor != null ? currentWildColor : "None"));
        for (Player player : players) {
            System.out.println(player.getName() + " has " + player.getHand().size() + " cards.");
        }
        System.out.println("=========================");
    }
}
