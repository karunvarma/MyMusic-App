package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AlbumController {
    Album album;
    User user;

    @FXML
    ImageView albumImageView;

    @FXML
    Label albumNameLabel;

    @FXML
    Label albumArtistLabel;

    @FXML
    Label albumYearLabel;

    @FXML
    Label albumGenreLabel;

    @FXML
    HBox albumTracksContent;

    @FXML
    public void goBack() {
        PageChanger.getInstance().goToSearchPage(albumNameLabel.getScene(), user);
    }



    public void setUp(Album album, User user) {
        this.album = album;
        this.user = user;

        if (album != null) {
            Image image = new Image(album.getImagePath());
            albumImageView.setImage(image);
            albumNameLabel.setText(album.getName());
            albumArtistLabel.setText(album.getArtistName());
            albumYearLabel.setText(album.getYear()+"");
            albumGenreLabel.setText(album.getGenre());
            albumTracksContent.getChildren().clear();
            System.out.println((album.getTracks().get(0).getName()));
            TableView tableView = new TrackTableView(album.getTracks());
            tableView.setMinWidth(1600);
            albumTracksContent.getChildren().add(tableView);

        }
    }
}
