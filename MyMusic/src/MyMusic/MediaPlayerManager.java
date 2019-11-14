package MyMusic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaPlayerManager {
    private static MediaPlayer mediaPlayer;
    private static int playIndex;

    public static void playTrack(Track track) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        File f = new File(track.getMediaPath());
        Media m = new Media(f.toURI().toString());
        mediaPlayer = new MediaPlayer(m);
        mediaPlayer.play();
    }

    public static void playAlbum(Album album) {
        if (playIndex < album.getTracks().size()) {
            playTrack(album.getTracks().get(playIndex));

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    playIndex++;
                    playAlbum(album);
                }
            });
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
