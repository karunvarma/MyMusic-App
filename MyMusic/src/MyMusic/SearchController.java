package MyMusic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

public class SearchController {
    @FXML
    private VBox genreBox;

    @FXML
    private TextField searchField;

    @FXML
    private VBox searchResultsBox;

    @FXML
    private CheckBox trackCheckBox;

    @FXML
    private CheckBox albumCheckBox;

    @FXML
    private CheckBox artistCheckBox;

    @FXML
    private CheckBox allGenresCheckBox;




    @FXML
    private void search() {
        // Search string
        String searchString = searchField.getText();

        // Search criteria flags
        Boolean searchByTrack = trackCheckBox.isSelected();
        Boolean searchByAlbum = albumCheckBox.isSelected();
        Boolean searchByArtist = artistCheckBox.isSelected();
        Boolean searchByAllGenres = allGenresCheckBox.isSelected();


        // Use DatabaseManager to send search query and receive results.

        // Remove any previous search results
        searchResultsBox.getChildren().clear();


        ArrayList<Album> albums = new ArrayList<Album>();
        albums.add(new Album("Name", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name2", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name3", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name4", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name5", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name6", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name7", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name8", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name9", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name10", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name2", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name3", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name4", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name5", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name6", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name7", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name8", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name9", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name10", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name2", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name3", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name4", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name5", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name6", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name7", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name8", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name9", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));
        albums.add(new Album("Name10", 2019, "Pop", "MyMusic/fxml/musical-note.jpg", 100));


        ArrayList<Artist> artists = new ArrayList<Artist>();
        artists.add(new Artist("Name", "MyMusic/fxml/musical-note.jpg"));
        artists.add(new Artist("Name1", "MyMusic/fxml/musical-note.jpg"));
        artists.add(new Artist("Name2", "MyMusic/fxml/musical-note.jpg"));
        artists.add(new Artist("Name3", "MyMusic/fxml/musical-note.jpg"));
        artists.add(new Artist("Name4", "MyMusic/fxml/musical-note.jpg"));

        ArrayList<Track> tracks = new ArrayList<Track>();
        Track track = new Track();
        track.setName("Name");
        track.setAlbumName("Album");
        track.setArtistName("Artist");
        track.setNumPlays(0);
        track.setGenre("Genre");
        tracks.add(track);

        searchResultsBox.getChildren().addAll(getAlbumResults(albums), getArtistResults(artists), getTrackResults(tracks));
    }


    private Node getTrackResults(List<Track> trackList) {
        // VBox that will contain everything and be returned
        VBox trackResults = new VBox();
        trackResults.setSpacing(5);
        trackResults.setPadding(new Insets(5,5,5,5));

        // Label for album results
        Label tracksLabel = new Label("Tracks");
        tracksLabel.setStyle("-fx-font-size: 28;");
        trackResults.getChildren().add(tracksLabel);

        // Scroll pane
        ScrollPane trackScrollPane = new ScrollPane();
        trackScrollPane.setMaxHeight(800);
        trackScrollPane.setFitToHeight(true);


        trackScrollPane.setContent(new TrackTableView(trackList));
        trackResults.getChildren().add(trackScrollPane);

        return trackResults;
    }

    private Node getAlbumResults(List<Album> albumList) {
        // VBox that will contain everything and be returned
        VBox albumResults = new VBox();
        albumResults.setSpacing(5);
        albumResults.setPadding(new Insets(5,5,5,5));

        // Label for album results
        Label albumsLabel = new Label("Albums");
        albumsLabel.setStyle("-fx-font-size: 28;");
        albumResults.getChildren().add(albumsLabel);

        // Scroll pane
        ScrollPane albumScrollPane = new ScrollPane();
        albumScrollPane.setMaxHeight(400);
        albumScrollPane.setFitToHeight(true);

        // Album results in a VBox that will be the content of the scroll pane
        VBox albumVBox = new VBox();

        // Row of album item boxes
        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(10,10,10,10));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= albumList.size(); i++) {
            if (i == albumList.size()) {
                albumVBox.getChildren().add(itemBoxRow);
                break;
            }

            try {
                ItemBox itemBox = new ItemBox(albumList.get(i));
                itemBoxRow.getChildren().add(itemBox);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Add item HBox row to search results box after every 9th item
            if ((i + 1) % 9 == 0) {
                albumVBox.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(10,10,10,10));
                itemBoxRow.setSpacing(20);
            }
        }
        albumScrollPane.setContent(albumVBox);
        albumResults.getChildren().add(albumScrollPane);

        return albumResults;
    }

    private Node getArtistResults(List<Artist> artistList) {
        // VBox that will contain everything and be returned
        VBox artistResults = new VBox();
        artistResults.setSpacing(5);
        artistResults.setPadding(new Insets(5,5,5,5));

        // Label for album results
        Label artistLabel = new Label("Artists");
        artistLabel.setStyle("-fx-font-size: 28;");
        artistResults.getChildren().add(artistLabel);

        // Scroll pane
        ScrollPane artistScrollPane = new ScrollPane();
        artistScrollPane.setMaxHeight(400);
        artistScrollPane.setFitToHeight(true);
        //artistScrollPane.setFitToWidth(false);

        // Artist results in a VBox that will be the content of the scroll pane
        VBox artistVBox = new VBox();

        // Row of artist item boxes
        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(10,10,10,10));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= artistList.size(); i++) {
            if (i == artistList.size()) {
                artistVBox.getChildren().add(itemBoxRow);
                break;
            }

            try {
                ItemBox itemBox = new ItemBox(artistList.get(i));
                itemBoxRow.getChildren().add(itemBox);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Add item HBox row to search results box after every 9th item
            if ((i + 1) % 9 == 0) {
                artistVBox.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(10,10,10,10));
                itemBoxRow.setSpacing(20);
            }
        }
        artistScrollPane.setContent(artistVBox);
        artistResults.getChildren().add(artistScrollPane);


        return artistResults;
    }

    @FXML
    public void goToHomePage() {
        changePage("fxml/home.fxml");
    }




    public void changePage(String fxmlPath)  {
        try {
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = searchField.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
