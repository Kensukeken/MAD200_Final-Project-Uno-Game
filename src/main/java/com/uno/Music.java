package com.uno;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * @author: Mahta Haghbin
 * @date: March 30th, 2025
 * @Filename: Music.java
 *
 * @Description: This will control the background music.
 */

public class Music {
    private static MediaPlayer mediaPlayer;

    public static void playMusic(String folder) {
        if (mediaPlayer == null) {
            Media media = new Media(new File(folder).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
