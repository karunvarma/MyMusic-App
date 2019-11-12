package MyMusic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class AlbumEditBox extends HBox {
    private TextField nameField;
    private TextField imagePathField;
    private TextField genreField;
    private TextField yearField;
    private TextField ratingField;
    private Button saveButton;
    private Button cancelButton;

    private Album album;

    public AlbumEditBox(Album album) {
        this.album = album;
        nameField = new TextField(album.getName());
        imagePathField = new TextField(album.getImagePath());
        genreField = new TextField(album.getGenre());
        yearField = new TextField(album.getYear()+"");
        ratingField = new TextField(album.getRating()+"");

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
        genreField.setPrefWidth(150);
        yearField.setPrefWidth(150);
        ratingField.setPrefWidth(150);

        getChildren().addAll(nameField, imagePathField, genreField, yearField, ratingField, saveButton, cancelButton);
    }

    public Album getAlbum() {
        album.setName(nameField.getText());
        album.setImagePath(imagePathField.getText());
        album.setGenre(genreField.getText());
        album.setYear(Integer.parseInt(yearField.getText()));
        album.setRating(Float.parseFloat(ratingField.getText()));
        return album;
    }

    public boolean validateYearField() {
        boolean isValid = false;

        return isValid;
    }

    public boolean validateRatingField() {
        boolean isValid = false;

        return isValid;
    }

    public void save() {
        DatabaseManager databaseManager = null;
        try {
            databaseManager = new DatabaseManager();
            databaseManager.updateAlbum(getAlbum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetChildren() {
        nameField.setText(album.getName());
        imagePathField.setText(album.getImagePath());
        genreField.setText(album.getGenre());
        yearField.setText(album.getYear()+"");
        ratingField.setText(album.getRating()+"");
    }
}
