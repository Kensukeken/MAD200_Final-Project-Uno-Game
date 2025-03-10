package com.uno;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void receiveCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
        }
    }


    @Override
    public String toString() {
        return name + " with hand: " + hand;
    }
}
