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


    public ItemLayout(String imagePath, int width, int height) throws FileNotFoundException {
        image = new Image(new FileInputStream(imagePath));
        imageView = new ImageView(image);


    }
}
