package com.uno.Additional_GameLogic_Files;

import java.io.File;
import javafx.scene.image.Image;

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
