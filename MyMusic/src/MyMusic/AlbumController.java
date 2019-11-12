package MyMusic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AlbumController {
    private Album album;
    private User user;
    boolean inEditMode;

    @FXML
    private ImageView albumImageView;
    @FXML
    private Label albumNameLabel;
    @FXML
    private Label albumArtistLabel;
    @FXML
    private Label albumYearLabel;
    @FXML
    private Label albumGenreLabel;
    @FXML
    private HBox albumTracksContent;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField albumNameTextField;
    @FXML
    private TextField imagePathTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField genreTextField;


    @FXML
    public void toggleEditMode() {
        if (inEditMode) {
            inEditMode = false;
            albumNameLabel.setManaged(true);
            albumNameLabel.setVisible(true);
            albumArtistLabel.setManaged(true);
            albumArtistLabel.setVisible(true);
            albumYearLabel.setManaged(true);
            albumYearLabel.setVisible(true);
            albumGenreLabel.setManaged(true);
            albumGenreLabel.setVisible(true);
            editButton.setManaged(true);
            editButton.setVisible(true);
            saveButton.setManaged(false);
            saveButton.setVisible(false);
            albumNameTextField.setManaged(false);
            albumNameTextField.setVisible(false);
            imagePathTextField.setManaged(false);
            imagePathTextField.setVisible(false);
            yearTextField.setManaged(false);
            yearTextField.setVisible(false);
            genreTextField.setManaged(false);
            genreTextField.setVisible(false);
        }
        else {
            inEditMode = true;
            albumNameLabel.setManaged(false);
            albumNameLabel.setVisible(false);
            albumArtistLabel.setManaged(false);
            albumArtistLabel.setVisible(false);
            albumYearLabel.setManaged(false);
            albumYearLabel.setVisible(false);
            albumGenreLabel.setManaged(false);
            albumGenreLabel.setVisible(false);
            editButton.setManaged(true);
            editButton.setVisible(true);
            saveButton.setManaged(true);
            saveButton.setVisible(true);
            albumNameTextField.setManaged(true);
            albumNameTextField.setVisible(true);
            imagePathTextField.setManaged(true);
            imagePathTextField.setVisible(true);
            yearTextField.setManaged(true);
            yearTextField.setVisible(true);
            genreTextField.setManaged(true);
            genreTextField.setVisible(true);

            albumNameTextField.setText(album.getName());
            imagePathTextField.setText(album.getImagePath());
            yearTextField.setText(album.getYear()+"");
            genreTextField.setText(album.getGenre());

        }
    }

    @FXML
    private void save() {
        DatabaseManager databaseManager = null;
        try {
            databaseManager = new DatabaseManager();

            album.setName(albumNameTextField.getText());
            album.setImagePath(imagePathTextField.getText());
            album.setYear(Integer.parseInt(yearTextField.getText()));
            album.setGenre(genreTextField.getText());

            albumNameLabel.setText(album.getName());
            Image image = new Image(album.getImagePath());
            albumImageView.setImage(image);
            albumYearLabel.setText(album.getYear()+"");
            albumGenreLabel.setText(album.getGenre());

            databaseManager.updateAlbum(album);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void goBack() {
        PageChanger.getInstance().goToSearchPage(albumNameLabel.getScene(), user);
    }

    public void setUp(Album album, User user) {
        this.album = album;
        this.user = user;
        inEditMode = false;

        if (album != null) {
            Image image = new Image(album.getImagePath());
            albumImageView.setImage(image);
            albumNameLabel.setText(album.getName());
            albumArtistLabel.setText(album.getArtistName());
            albumYearLabel.setText(album.getYear()+"");
            albumGenreLabel.setText(album.getGenre());
            albumTracksContent.getChildren().clear();
            TableView tableView = new TrackTableView(album.getTracks());
            tableView.setMinWidth(1600);
            albumTracksContent.getChildren().add(tableView);
        }

        // Admin
        if (user.isAdmin()) {
            editButton.setVisible(true);
            editButton.setManaged(true);
        }
        else {
            editButton.setVisible(false);
            editButton.setManaged(false);
        }
    }
}
