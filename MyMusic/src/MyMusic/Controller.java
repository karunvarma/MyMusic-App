package MyMusic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import java.io.IOException;

public class Controller {
    @FXML
    VBox yourMusicContent;

    @FXML
    public void goToSearchPage() {
        changePage("fxml/search.fxml");
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
}
