package MyMusic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private User user;

    private TrackTableView trackTableView;
    private VBox albumVBox;
    private VBox artistVBox;

    @FXML
    VBox yourMusicContent;

    @FXML
    public void goToSearchPage() {
        changePage("fxml/search.fxml");
    }


    @FXML
    private void displayTracks() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));

        //setTrackTableView(tracks);

        yourMusicContent.getChildren().add(trackTableView);
    }

    @FXML
    private void displayAlbums() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));

        //setAlbumVBox(albums);

        yourMusicContent.getChildren().add(albumVBox);
    }

    @FXML
    private void displayArtists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));


        //setArtistVBox(artists);
        yourMusicContent.getChildren().add(artistVBox);
    }

    @FXML
    private void displayPlaylists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));

    }


    public void setTrackTableView(List<Track> trackList) {
        trackTableView = new TrackTableView(trackList);
    }

    public void setAlbumVBox(List<Album> albumList) {
        albumVBox = new VBox();
        albumVBox.setSpacing(5);
        albumVBox.setPadding(new Insets(5,5,5,5));

        for (int i = 0; i <= albumList.size(); i++) {
            try {
                HBox albumHBox = new HBox();
                albumHBox.setSpacing(50);
                ItemBox albumBox = new ItemBox(albumList.get(i), 500);

                albumHBox.getChildren().addAll(albumBox, trackTableView);
                albumVBox.getChildren().add(albumHBox);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setArtistVBox(List<Artist> artistList) {

    }



    public void changePage(String fxmlPath)  {
        try {
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = yourMusicContent.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUser(User user) {
        this.user = user;
    }
}
