package com.uno;

import java.io.File;
import javafx.scene.image.Image;
/**
 * @author: Yuanyang Chen
 * @date: March 2nd, 2025
 * @Filename: CardLoader.java
 *
 * @Description: This is cardloader class for the game logic.
 * */

import java.util.HashMap;
import java.util.Map;

public class CardLoader {
    private static final Map<String, Image> cardImages = new HashMap<>();
    private static final String CARD_IMAGE_PATH = "/cards/"; // Assuming images are in resources/cards folder

    public static Image loadCardImage(String imageName) {
        // Check if image is already loaded
        if (cardImages.containsKey(imageName)) {
            return cardImages.get(imageName);
        }

        try {
            // Load the image from resources
            Image image = new Image(CardLoader.class.getResourceAsStream(CARD_IMAGE_PATH + imageName));
            cardImages.put(imageName, image);
            return image;
        } catch (Exception e) {
            System.err.println("Could not load image: " + imageName);
            e.printStackTrace();
            // Return a default image or null
            return null;
        }
    }
}