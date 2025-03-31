package com.uno.Additional_GameLogic_Files;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author: Yuanyang Chen
 * @date: March 25th,  2025
 * @Filename: PlayerStateManager.java
 *
 * @Description: This class represents the player state manager .
 * */

public class PlayerStateManager {
    private final List<Player> playerList; // List of players
    private int currentPlayerIndex; // Current player's index

    private GameDirection playDirection; // Current game direction

    public PlayerStateManager(List<Player> players) {
        this.playerList = players;
        this.currentPlayerIndex = 0;
        this.playDirection = GameDirection.CLOCKWISE; // Default direction
    }

    // Get the player whose turn it is
    public Player getCurrentPlayer() {
        return playerList.get(currentPlayerIndex);
    }

    // Move to the next player
    public Player moveToNextPlayer() {
        currentPlayerIndex = calculateNextPlayerIndex();
        return getCurrentPlayer();
    }

    // Reverse the play direction
    public void reversePlayDirection() {
        playDirection = (playDirection == GameDirection.CLOCKWISE)
                ? GameDirection.COUNTERCLOCKWISE
                : GameDirection.CLOCKWISE;
    }

    // Get all players
    public List<Player> getAllPlayers() {
        return playerList;
    }

    // Find a player by UUID
    public Player findPlayerById(UUID id) {
        for (Player player : playerList) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    // Calculate the next player's index
    private int calculateNextPlayerIndex() {
        int playerCount = playerList.size();
        if (playDirection == GameDirection.CLOCKWISE) {
            return (currentPlayerIndex + 1) % playerCount;
        } else {
            return (currentPlayerIndex - 1 + playerCount) % playerCount;
        }
    }

    public List<Player> getPlayers() {
        return playerList;
    }

    public GameDirection getPlayDirection() {
        return playDirection;
    }

    public static void main(String[] args) {
        // Create players
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        Player player4 = new Player("Player4");

        // Add players to the game
        List<Player> players = Arrays.asList(player1, player2, player3, player4);
        PlayerStateManager stateManager = new PlayerStateManager(players);

        // Initial check
        System.out.println("Current player: " + stateManager.getCurrentPlayer().getName());

        // Move forward through all players
        stateManager.moveToNextPlayer();
        System.out.println("Next player: " + stateManager.getCurrentPlayer().getName());

        stateManager.moveToNextPlayer();
        System.out.println("Next player: " + stateManager.getCurrentPlayer().getName());

        stateManager.moveToNextPlayer();
        System.out.println("Next player: " + stateManager.getCurrentPlayer().getName());

        // Check if it cycles back correctly
        stateManager.moveToNextPlayer();
        System.out.println("Back to first player: " + stateManager.getCurrentPlayer().getName());

        // Reverse direction and check multiple turns
        stateManager.reversePlayDirection();
        System.out.println("Direction reversed!");

        stateManager.moveToNextPlayer();
        System.out.println("After reversing, next player: " + stateManager.getCurrentPlayer().getName());

        stateManager.moveToNextPlayer();
        System.out.println("Next player after reversing: " + stateManager.getCurrentPlayer().getName());

        // Check multiple cycles in reverse
        stateManager.moveToNextPlayer();
        System.out.println("Next player in reverse: " + stateManager.getCurrentPlayer().getName());

        stateManager.moveToNextPlayer();
        System.out.println("Back to first player in reverse: " + stateManager.getCurrentPlayer().getName());

        // Find a player by UUID
        UUID searchId = player3.getId();
        Player foundPlayer = stateManager.findPlayerById(searchId);
        System.out.println("Found player by ID: " + (foundPlayer != null ? foundPlayer.getName() : "Not found"));
    }
}

