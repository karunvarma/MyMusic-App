package MyMusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
       VBox root1 = new VBox();

        // Set up layout
        root1.getChildren().addAll(new TopBar(), new SearchLayout());
        Parent root = FXMLLoader.load(getClass().getResource("fxml/album.fxml"));

        // Set the title
        primaryStage.setTitle("My Music");

        scene = new Scene(root, Screen.getPrimary().getBounds().getMaxX(), Screen.getPrimary().getBounds().getMaxY());

        // Set the initial scene
        primaryStage.setScene(scene);

        scene.getStylesheets().add(Main.class.getResource("css/styles.css").toExternalForm());
        //scene.setRoot(root1);
        // Show the scene
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void changePage(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        scene.setRoot(root);
    }

}
