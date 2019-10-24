package MyMusic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import java.io.IOException;

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
