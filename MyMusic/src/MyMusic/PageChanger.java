package MyMusic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.annotation.Resource;
import java.io.IOException;

public class PageChanger {


    public void goToPlaylistPage(Scene scene, Playlist playlist, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/playlist.fxml"));
            root = loader.load();

            // Get the controller of the new root
            PlaylistController playlistController = loader.getController();

            // Set property of the controller
            playlistController.setUp(playlist, user);

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToHomePage(Scene scene, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
            root = loader.load();

            // Get the controller of the new root
            Controller controller = loader.getController();

            // Set property of the controller
            controller.setUser(user);

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToSignUpPage(Scene scene) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/signup.fxml"));
            root = loader.load();

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToLoginPage(Scene scene) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
            root = loader.load();

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
