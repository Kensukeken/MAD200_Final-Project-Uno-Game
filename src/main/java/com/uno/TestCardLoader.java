package com.uno;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * @author: Yuanyang Chen
 * @date: March 2nd, 2025
 * @Filename: TestCardLoader.java
 *
 * @Description: This represents the test card loader for the game logic.
 */
public class TestCardLoader extends Application {
    @Override
    public void start(Stage primaryStage) {
        String testImage = "blue_0_card.png";
        System.out.println("Testing image: " + testImage);
        Image image = CardLoader.loadCardImage(testImage);

        if (image == null) {
            System.out.println("Test failed: Image is null");
        } else {
            System.out.println("Test passed: Image loaded successfully");
        }

        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
