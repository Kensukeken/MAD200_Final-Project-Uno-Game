package com.uno.Additional_GameLogic_Files;

import java.util.List;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: GameSession.java
 *
 * @Description: This class represents the game session of the game
 * */
public class GameSession {
    private final PlayerStateManager playerStateManager;
    private final PileManager pileManager;
    private Player winner = null;

    public GameSession(List<Player> players, List<Card> initialCards) {
        this.playerStateManager = new PlayerStateManager(players);
        this.pileManager = new PileManager(initialCards);
        startDiscardPile();
    }

    private void startDiscardPile() {
        Card firstCard;
        do {
            firstCard = pileManager.drawCard();
        } while (firstCard.getType() == CardType.WILD_DRAW_FOUR_CARDS); // Prevent WILD_DRAW_FOUR

        pileManager.discardCard(firstCard);  // Set the first discard card

        // Handle special effects of the first card
        switch (firstCard.getType()) {
            case SKIP -> playerStateManager.moveToNextPlayer();
            case REVERSE -> playerStateManager.reversePlayDirection();
            case DRAW_TWO -> {
                Player next = playerStateManager.moveToNextPlayer();
                drawCards(next, 2);
            }
        }

        // Now, deal cards to players
        dealInitialCards(playerStateManager.getPlayers());
    }


    public Player getCurrentPlayer() {
        return playerStateManager.getCurrentPlayer();
    }

    public boolean isGameOver() {
        return winner != null;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean playCard(Player player, Card card) {
        if (isGameOver()) return false;
        if (!player.equals(getCurrentPlayer())) return false;
        if (!player.ownsCard(card)) return false;

        Card topCard = pileManager.peekTopDiscard();
        if (!isValidPlay(card, topCard)) return false;

        player.discardCard(card);
        pileManager.discardCard(card);

        // Trigger CardPlayedEvent
        GameEventPublisher.publish(new GameEventPublisher.CardPlayedEvent(player, card));

        if (player.countCards() == 0) {
            winner = player;
            System.out.println(player.getName() + " wins the game!");

            // Trigger GameOverEvent
            GameEventPublisher.publish(new GameEventPublisher.GameOverEvent(player));
            return true;
        }

        handleCardEffect(card);
        return true;
    }

    private boolean isValidPlay(Card card, Card topCard) {
        return card.getColor() == topCard.getColor() || card.getType() == topCard.getType()
                || card.getType() == CardType.WILD_COLOR || card.getType() == CardType.WILD_DRAW_FOUR_CARDS;
    }

    private void handleCardEffect(Card card) {
        switch (card.getType()) {
            case SKIP -> playerStateManager.moveToNextPlayer();
            case REVERSE -> playerStateManager.reversePlayDirection();
            case DRAW_TWO -> {
                Player nextPlayer = playerStateManager.moveToNextPlayer();
                drawCards(nextPlayer, 2);
            }
            case WILD_DRAW_FOUR_CARDS -> {
                Player nextPlayer = playerStateManager.moveToNextPlayer();
                drawCards(nextPlayer, 4);
            }
        }

        // Trigger TurnChangedEvent
        GameEventPublisher.publish(new GameEventPublisher.TurnChangedEvent(playerStateManager.getCurrentPlayer()));

        playerStateManager.moveToNextPlayer();
    }

    public void drawCard(Player player) {
        Card drawnCard = pileManager.drawCard();
        if (drawnCard != null) {
            player.addCard(drawnCard);

            // Trigger CardDrawnEvent
            GameEventPublisher.publish(new GameEventPublisher.CardDrawnEvent(player, drawnCard));

            autoPlayIfValid(player, drawnCard);
        }
    }

    private void drawCards(Player player, int count) {
        for (int i = 0; i < count; i++) drawCard(player);
    }

    private void autoPlayIfValid(Player player, Card drawnCard) {
        Card topCard = pileManager.peekTopDiscard();
        if (isValidPlay(drawnCard, topCard)) {
            playCard(player, drawnCard);
        }
    }

    private void processAITurn(Player aiPlayer) {
        List<Card> handCards = aiPlayer.getPlayerCardList().getCardList();
        boolean played = false;

        for (Card card : handCards) {
            if (playCard(aiPlayer, card)) {
                played = true;
                break;
            }
        }

        if (!played) {
            drawCard(aiPlayer);
        }
    }

    public void playGame() {
        while (!isGameOver()) {
            Player currentPlayer = getCurrentPlayer();
            System.out.println("Current Player: " + currentPlayer.getName());

            if (currentPlayer.isAI()) {
                processAITurn(currentPlayer);
            } else {
                System.out.println(currentPlayer.getName() + "'s turn.");
            }
        }

        System.out.println("Game Over! Winner: " + getWinner().getName());
    }

    public Card getTopCard() {
        return pileManager.peekTopDiscard();
    }


    private void dealInitialCards(List<Player> players) {
        for (Player player : players) {
            drawCards(player, 7);
        }
    }

    public static void main(String[] args) {
        // Create players
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player("Charlie");

        // Create deck and deal cards
        List<Card> initialDeck = new UnoCardDeck().getDeck();
        GameSession gameSession = new GameSession(List.of(player1, player2, player3), initialDeck);

        // Print starting info
        System.out.println("Starting game with players: Alice, Bob, Charlie");
        System.out.println("Initial top card: " + gameSession.pileManager.peekTopDiscard());

        // Subscribe to events (for logging)
        GameEventPublisher.subscribe(event -> {
            if (event instanceof GameEventPublisher.CardPlayedEvent e) {
                System.out.println("LOG: " + e.player.getName() + " played " + e.card);
            } else if (event instanceof GameEventPublisher.CardDrawnEvent e) {
                System.out.println("LOG: " + e.player.getName() + " drew a card.");
            } else if (event instanceof GameEventPublisher.TurnChangedEvent e) {
                System.out.println("LOG: Turn changed to " + e.newPlayer.getName());
            } else if (event instanceof GameEventPublisher.GameOverEvent e) {
                System.out.println("LOG: Game over! Winner: " + e.winner.getName());
            }
        });

        // Simulate playing a few rounds
        gameSession.playCard(player1, new Card(CardType.NUMBER, CardColor.RED, 5));
        gameSession.playCard(player2, new Card(CardType.NUMBER, CardColor.RED, 7));
        gameSession.playCard(player3, new Card(CardType.SKIP, CardColor.BLUE, -1));
        gameSession.playCard(player1, new Card(CardType.REVERSE, CardColor.YELLOW, -1));

        // Continue until game is over
        while (!gameSession.isGameOver()) {
            Player currentPlayer = gameSession.getCurrentPlayer();
            System.out.println("Current Player: " + currentPlayer.getName());

            List<Card> handCards = currentPlayer.getPlayerCardList().getCardList();
            boolean played = false;

            for (Card card : handCards) {
                if (gameSession.playCard(currentPlayer, card)) {
                    played = true;
                    break;
                }
            }

            if (!played) {
                System.out.println(currentPlayer.getName() + " has no valid cards. Drawing a card...");
                gameSession.drawCard(currentPlayer);
            }
        }

        // Print final winner
        System.out.println("Game Over! Winner: " + gameSession.getWinner().getName());
    }
}
