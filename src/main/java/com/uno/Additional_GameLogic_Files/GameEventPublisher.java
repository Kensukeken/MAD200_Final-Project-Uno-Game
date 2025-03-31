package com.uno.Additional_GameLogic_Files;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: GameEventPublisher.java
 *
 * @Description: A class represents the game event publisher
 * */
public class GameEventPublisher {
    private static final List<GameEventListener> listeners = new ArrayList<>();

    // Subscribe to events
    public static void subscribe(GameEventListener listener) {
        listeners.add(listener);
    }

    // Unsubscribe from events
    public static void unsubscribe(GameEventListener listener) {
        listeners.remove(listener);
    }

    // Publish an event
    public static void publish(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    // Event Listener (Functional Interface)
    public interface GameEventListener {
        void onEvent(GameEvent event);
    }

    // Base class for events
    public static abstract class GameEvent {}

    // Card Played Event
    public static class CardPlayedEvent extends GameEvent {
        public final Player player;
        public final Card card;

        public CardPlayedEvent(Player player, Card card) {
            this.player = player;
            this.card = card;
        }
    }

    // Card Drawn Event
    public static class CardDrawnEvent extends GameEvent {
        public final Player player;
        public final Card card;

        public CardDrawnEvent(Player player, Card card) {
            this.player = player;
            this.card = card;
        }
    }

    // Turn Changed Event
    public static class TurnChangedEvent extends GameEvent {
        public final Player newPlayer;

        public TurnChangedEvent(Player newPlayer) {
            this.newPlayer = newPlayer;
        }
    }

    // Game Over Event
    public static class GameOverEvent extends GameEvent {
        public final Player winner;

        public GameOverEvent(Player winner) {
            this.winner = winner;
        }
    }
}
