package MyMusic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ItemBox extends VBox {
    private ImageView imageView;
    private Label label1;
    private Label label2;

    public ItemBox(Album album) throws FileNotFoundException {

        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(135);
        imageView.setFitHeight(135);

        Image image = new Image("MyMusic/fxml/musical-note.jpg");
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(album.getName());

        // Set label 2 (artist)
        label2 = new Label(album.getName());


        // Add children
        getChildren().addAll(imageView, label1, label2);

        // Style
        setMaxSize(200,200);
    }

    public ItemBox(Album album, int size) throws FileNotFoundException {

        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        Image image = new Image("MyMusic/fxml/musical-note.jpg");
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(album.getName());

        // Set label 2 (artist)
        label2 = new Label(album.getName());


        // Add children
        getChildren().addAll(imageView, label1, label2);

        // Style
        setMaxSize(size + 100,size + 100);
    }

    public ItemBox(Artist artist) throws FileNotFoundException {

        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(135);
        imageView.setFitHeight(135);

        Image image = new Image("MyMusic/fxml/musical-note.jpg");
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(artist.getName());


        // Add children
        getChildren().addAll(imageView, label1);

        // Style
        setMaxSize(200,200);

    }
}
