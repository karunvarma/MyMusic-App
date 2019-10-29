package MyMusic;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private User user;

    private TrackTableView trackContent;
    private VBox albumContent;
    private VBox artistContent;
    private VBox playlistContent;

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

        yourMusicContent.getChildren().add(trackContent);
    }

    @FXML
    private void displayAlbums() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));

        //setAlbumVBox(albums);

        yourMusicContent.getChildren().add(albumContent);
    }

    @FXML
    private void displayArtists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));


        //setArtistVBox(artists);
        yourMusicContent.getChildren().add(artistContent);
    }

    @FXML
    private void displayPlaylists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(playlistContent);
    }


    public void setTrackContent(List<Track> trackList) {
        trackContent = new TrackTableView(trackList);
    }

    public void setAlbumContent(List<Album> albumList) {
        albumContent = new VBox();
        albumContent.setSpacing(5);
        albumContent.setPadding(new Insets(5,5,5,5));

        for (int i = 0; i <= albumList.size(); i++) {
            try {
                HBox albumHBox = new HBox();
                albumHBox.setSpacing(50);
                ItemBox albumBox = new ItemBox(albumList.get(i), 500);

                albumHBox.getChildren().addAll(albumBox, trackContent);
                albumContent.getChildren().add(albumHBox);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setArtistContent(List<Artist> artistList) {

    }


    public void setPlaylistContent() {
        VBox content = new VBox();

        // Row of playlist item boxes
        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(15,35,15,35));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= user.getPlaylists().size(); i++) {
            if (i == user.getPlaylists().size()) {
                content.getChildren().add(itemBoxRow);
                break;
            }

            try {
                ItemBox itemBox = new ItemBox(user.getPlaylists().get(i));
                itemBoxRow.getChildren().add(itemBox);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Add item HBox row to search results box after every 7th item
            if ((i + 1) % 7 == 0) {
                content.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(15,35,15,35));
                itemBoxRow.setSpacing(20);
            }
        }

        // Add playlist button
        HBox buttonRow = new HBox();
        buttonRow.setPadding(new Insets(15,35,15,35));
        Button addButton = new Button("Add New Playlist");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PageChanger pageChanger = new PageChanger();
                pageChanger.goToPlaylistPage(yourMusicContent.getScene(), null);
            }
        });
        buttonRow.getChildren().add(addButton);
        content.getChildren().add(buttonRow);

        playlistContent = content;
    }


    public void changePage(String fxmlPath)  {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            root = loader.load();

            // Get the controller of the new root
            SearchController controller = loader.getController();

            // Set user property of the controller
            controller.setUser(user);

            Scene scene = yourMusicContent.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUser(User user) {
        this.user = user;
        setPlaylistContent();
    }
}
