package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    HBox playlistTracksContent;



    @FXML
    public void goBack() {
        PageChanger.getInstance().goToHomePage(playlistImageView.getScene(), user);
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
            playlistTracksContent.getChildren().clear();
            TableView tableView = new TrackTableView(playlist.getTracks());
            tableView.setMinWidth(1600);
            playlistTracksContent.getChildren().add(tableView);
        }
    }
}
