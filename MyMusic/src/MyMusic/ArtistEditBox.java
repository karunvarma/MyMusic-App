package MyMusic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class ArtistEditBox extends HBox {
    private TextField nameField;
    private TextField imagePathField;
    private TextField ratingField;
    private Button saveButton;
    private Button cancelButton;

    private Artist artist;

    public ArtistEditBox(Artist artist) {
        this.artist = artist;
        nameField = new TextField(artist.getName());
        imagePathField = new TextField(artist.getImagePath());
        ratingField = new TextField(artist.getRating()+"");

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                save();
            }
        });

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetChildren();
            }
        });

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        imagePathField.setPrefWidth(400);
        ratingField.setPrefWidth(150);

        getChildren().addAll(nameField, imagePathField, ratingField, saveButton, cancelButton);

    }

    public Artist getArtist() {
        artist.setName(nameField.getText());
        artist.setImagePath(imagePathField.getText());
        artist.setRating(Float.parseFloat(ratingField.getText()));
        return artist;
    }

    public void save() {
        DatabaseManager databaseManager = null;
        try {
            databaseManager = new DatabaseManager();
            databaseManager.updateArtist(getArtist());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetChildren() {
        nameField.setText(artist.getName());
        imagePathField.setText(artist.getImagePath());
        ratingField.setText(artist.getRating()+"");
    }
}
