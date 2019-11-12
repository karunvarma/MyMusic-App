package MyMusic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;


// Singleton class
public final class PageChanger {

    private static final PageChanger INSTANCE = new PageChanger();

    private PageChanger() {}

    public static PageChanger getInstance() {
        return INSTANCE;
    }



    public void goToArtistPage(Scene scene, Artist artist, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/artist.fxml"));
            root = loader.load();

            // Get the controller of the new root
            ArtistController controller = loader.getController();

            // Set property of the controller
            controller.setUp(artist, user);

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goToAlbumPage(Scene scene, Album album, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/album.fxml"));
            root = loader.load();

            // Get the controller of the new root
            AlbumController controller = loader.getController();

            // Set property of the controller
            controller.setUp(album, user);

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToPlaylistPage(Scene scene, Playlist playlist, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/playlist.fxml"));
            root = loader.load();

            // Get the controller of the new root
            PlaylistController controller = loader.getController();

            // Set property of the controller
            controller.setUp(playlist, user);

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
            controller.setUp(user);

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToSearchPage(Scene scene, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/search.fxml"));
            root = loader.load();

            // Get the controller of the new root
            SearchController controller = loader.getController();

            // Set property of the controller
            controller.setUser(user);

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAdminPage(Scene scene, User user) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/admin.fxml"));
            root = loader.load();

            // Get the controller of the new root
            AdminController controller = loader.getController();

            // Set property of the controller
            controller.setUp(user);

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
