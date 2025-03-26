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

public class CardLoader {
    private static final String CARD_FOLDER = "src/main/resources/cards/";

    public static Image loadCardImage(String fileName) {
        File file = new File(CARD_FOLDER + fileName);

        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return null;
        }

        System.out.println("Loading image from file: " + file.getAbsolutePath());
        return new Image(file.toURI().toString());
    }
}
