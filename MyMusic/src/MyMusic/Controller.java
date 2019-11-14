package MyMusic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private Label nameLabel;

    @FXML
    private Button adminButton;

    @FXML
    private VBox yourMusicContent;


    @FXML
    private void displayTracks() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(trackContent);
    }

    @FXML
    private void displayAlbums() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(albumContent);
    }

    @FXML
    private void displayArtists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(artistContent);
    }

    @FXML
    private void displayPlaylists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(playlistContent);
    }


    public void setTrackContent(List<Track> trackList) {
        trackContent = new TrackTableView(trackList, user);
    }

    public void setAlbumContent(List<Album> albumList) {
        albumContent = new VBox();
        albumContent.setSpacing(5);
        //albumContent.setPadding(new Insets(5,5,5,5));

        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(15,35,15,35));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= albumList.size(); i++) {
            if (i == albumList.size()) {
                albumContent.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Album album = albumList.get(i);
                ItemBox itemBox = new ItemBox(album);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToAlbumPage(yourMusicContent.getScene(), album, user);
                    }
                });
                itemBoxRow.getChildren().add(itemBox);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Add item HBox row to search results box after every 7th item
            if ((i + 1) % 7 == 0) {
                albumContent.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(15,35,15,35));
                itemBoxRow.setSpacing(20);
            }
        }
    }

    public void setArtistContent(List<Artist> artistList) {
        artistContent = new VBox();
        artistContent.setSpacing(5);
        //artistContent.setPadding(new Insets(5,5,5,5));

        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(15,35,15,35));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= artistList.size(); i++) {
            if (i == artistList.size()) {
                artistContent.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Artist artist = artistList.get(i);
                ItemBox itemBox = new ItemBox(artist);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToArtistPage(yourMusicContent.getScene(), artist, user);
                    }
                });
                itemBoxRow.getChildren().add(itemBox);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Add item HBox row to search results box after every 7th item
            if ((i + 1) % 7 == 0) {
                artistContent.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(15,35,15,35));
                itemBoxRow.setSpacing(20);
            }
        }
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
                Playlist playlist = user.getPlaylists().get(i);
                ItemBox itemBox = new ItemBox(playlist);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToPlaylistPage(yourMusicContent.getScene(), playlist, user);
                    }
                });
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
                Playlist playlist = new Playlist(0, "My Playlist", "MyMusic/fxml/musical-note.jpg", user.getUserId(), new ArrayList<Track>());
                PageChanger.getInstance().goToPlaylistPage(yourMusicContent.getScene(), playlist, user);
            }
        });
        buttonRow.getChildren().add(addButton);
        content.getChildren().add(buttonRow);

        playlistContent = content;
    }


    public void adminBtnAction() {
        PageChanger.getInstance().goToAdminPage(nameLabel.getScene(), user);
    }

    public void setUp(User user) {
        this.user = user;
        nameLabel.setText(user.getName());
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            setTrackContent(databaseManager.getUsersTracks(user));
            trackContent.setPrefHeight(1000);
            setAlbumContent(databaseManager.getUsersAlbums(user));
            setArtistContent(databaseManager.getUsersArtists(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPlaylistContent();

        if (user.isAdmin()) {
            adminButton.setVisible(true);
        }
    }

    @FXML
    public void goToSearchPage() {
        PageChanger.getInstance().goToSearchPage(yourMusicContent.getScene(), user);
    }

    @FXML
    public void logout() {
        PageChanger.getInstance().goToLoginPage(yourMusicContent.getScene());
    }
}
