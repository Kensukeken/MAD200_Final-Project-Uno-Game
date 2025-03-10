package com.uno;

import com.uno.Game;
import com.uno.Player;
import com.uno.StartScreen;
import javafx.application.Application;

import java.util.List;

public class GameLauncher {
    public static void main(String[] args) {
        Application.launch(StartScreen.class, args);
    }

    public static void startGame() {
        List<Player> players = List.of(new Player("Player 1"), new Player("Player 2"));
        Game game = new Game(players);
        game.displayGameState();
    }
}