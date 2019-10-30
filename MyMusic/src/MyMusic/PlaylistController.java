package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlaylistController {
    private Playlist playlist;
    private User user;

    @FXML
    ImageView playlistImageView;

    @FXML
    TextField playlistNameTextField;

    @FXML
    TextField playlistImagePathTextField;



    @FXML
    public void goBack() {
        PageChanger pageChanger = new PageChanger();
        pageChanger.goToHomePage(playlistImageView.getScene(), user);
    }


    @FXML
    public void savePlaylist() {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            playlist.setName(playlistNameTextField.getText());
            playlist.setImagePath(playlistImagePathTextField.getText());
            databaseManager.savePlaylist(playlist);
            user.setPlaylists(databaseManager.getPlaylists(user.getUserId()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void deletePlaylist() {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.deletePlaylist(playlist);
            user.setPlaylists(databaseManager.getPlaylists(user.getUserId()));
            goBack();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setUp(Playlist playlist, User user) {
        this.playlist = playlist;
        this.user = user;

        if (playlist != null) {
            Image image = new Image(playlist.getImagePath());
            playlistImageView.setImage(image);

            playlistNameTextField.setText(playlist.getName());
        }
    }
}
