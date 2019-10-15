package MyMusic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.layout.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();

        // Set up layout
        root.getChildren().addAll(new TopBar(), new SearchLayout());

        // Set the title
        primaryStage.setTitle("My Music");

        // Set the initial scene
        primaryStage.setScene(new Scene(root, Screen.getPrimary().getBounds().getMaxX(), Screen.getPrimary().getBounds().getMaxY()));

        // Show the scene
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
