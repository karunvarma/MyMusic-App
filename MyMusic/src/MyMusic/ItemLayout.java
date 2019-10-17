package MyMusic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ItemLayout extends VBox {
    private ImageView imageView;
    Image image;
    private Label label1;
    private Label label2;


    public ItemLayout(Album album) throws FileNotFoundException {

        // Set ImageView
       // image = new Image(new FileInputStream(album.getImagePath()));
        imageView = new ImageView();
        imageView.minWidth(100);
        imageView.minHeight(100);

        // Set label 1 (name)
        label1 = new Label(album.getName());


        // Set label 2 (artist)
        label2 = new Label(album.getName());


        // Add children
        getChildren().addAll(imageView, label1, label2);

        // Style
        setStyle("-fx-padding: 20; -fx-border-style: solid");
        setMaxSize(200,200);

    }
}
