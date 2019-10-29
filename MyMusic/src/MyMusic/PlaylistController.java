package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlaylistController {
    private Playlist playlist;

    @FXML
    ImageView playlistImageView;

    @FXML
    TextField playlistNameTextField;






    @FXML
    public void savePlaylist() {
        try {
            DatabaseManager databaseManager = new DatabaseManager();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;

        if (playlist != null) {
            Image image = new Image(playlist.getImagePath());
            playlistImageView.setImage(image);

            playlistNameTextField.setText(playlist.getName());
        }
    }
}
