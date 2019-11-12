package MyMusic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ArtistEditBox extends HBox {
    private TextField nameField;
    private TextField imagePathField;
    private TextField ratingField;
    private Button saveButton;
    private Button cancelButton;
    private Button deleteButton;

    private Artist artist;
    private boolean isNew;

    public ArtistEditBox() {
        isNew = true;
        this.artist = new Artist();
        nameField = new TextField();
        imagePathField = new TextField();
        ratingField = new TextField();

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        deleteButton = new Button("Delete");

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

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                delete();
            }
        });

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        imagePathField.setPrefWidth(400);
        ratingField.setPrefWidth(150);

        getChildren().addAll(nameField, imagePathField, ratingField, saveButton, cancelButton, deleteButton);
    }

    public ArtistEditBox(Artist artist) {
        isNew = false;
        this.artist = artist;
        nameField = new TextField(artist.getName());
        imagePathField = new TextField(artist.getImagePath());
        ratingField = new TextField(artist.getRating()+"");

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        deleteButton = new Button("Delete");

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

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                delete();
            }
        });

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        imagePathField.setPrefWidth(400);
        ratingField.setPrefWidth(150);

        getChildren().addAll(nameField, imagePathField, ratingField, saveButton, cancelButton, deleteButton);
    }

    public Artist getArtist() {
        artist.setName(nameField.getText());
        artist.setImagePath(imagePathField.getText());
        artist.setRating(Float.parseFloat(ratingField.getText()));
        return artist;
    }

    public void save() {
        DatabaseManager databaseManager = null;
        if (isNew) {
            try {
                databaseManager = new DatabaseManager();
                databaseManager.addArtist(getArtist());
                isNew = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                databaseManager = new DatabaseManager();
                databaseManager.updateArtist(getArtist());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void resetChildren() {
        if (isNew) {
            nameField.setText("");
            imagePathField.setText("");
            ratingField.setText("");
        }
        else {
            nameField.setText(artist.getName());
            imagePathField.setText(artist.getImagePath());
            ratingField.setText(artist.getRating()+"");
        }
    }

    private void delete() {
        DatabaseManager databaseManager = null;
        if (isNew) {
            ((VBox) getParent()).getChildren().remove(this);
        }
        else {
            try {
                databaseManager = new DatabaseManager();
                databaseManager.deleteArtist(getArtist());
                ((VBox) getParent()).getChildren().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
