package MyMusic;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaPlayerManager {
    private static MediaPlayer mediaPlayer;
    private static int playIndex;

    public static void playTrack(Track track, Button button) {
        if (track.isActiveTrack()) {
            mediaPlayer.play();
        }
        else {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            File f = new File(track.getMediaPath());
            Media m = new Media(f.toURI().toString());
            mediaPlayer = new MediaPlayer(m);
            mediaPlayer.play();
            track.setIsActiveTrack(true);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    track.setIsActiveTrack(false);
                }
            });
            mediaPlayer.setOnPaused(new Runnable() {
                @Override
                public void run() {
                    button.setText("Play");
                }
            });
            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    button.setText("Pause");
                }
            });
        }
    }

    public static void continuePlaying() {
        mediaPlayer.play();
    }

    public static void playAlbum(Album album, Button button) {
        if (playIndex < album.getTracks().size()) {
            playTrack(album.getTracks().get(playIndex), button);

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    playIndex++;
                    playAlbum(album, button);
                }
            });
            mediaPlayer.setOnPaused(new Runnable() {
                @Override
                public void run() {
                    button.setText("Play");
                }
            });
            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    button.setText("Pause");
                }
            });
        }
        else {
            playIndex = 0;
        }
    }

    public static void pause() {
        mediaPlayer.pause();
    }

    public static void stop() {
        mediaPlayer.stop();
        playIndex = 0;
    }
}
