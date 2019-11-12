package MyMusic;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class TrackEditBox extends HBox {
    private TextField nameField;
    private TextField mediaPathField;
    private TextField genreField;
    private TextField playsField;
    private TextField timeField;
    private Button saveButton;
    private Button cancelButton;

    private Track track;


    public TrackEditBox(Track track) {
        this.track = track;
        nameField = new TextField(track.getName());
        mediaPathField = new TextField(track.getMediaPath());
        genreField = new TextField(track.getGenre());
        playsField = new TextField(track.getNumPlays()+"");
        timeField = new TextField(track.getTime()+"");

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        mediaPathField.setPrefWidth(400);
        genreField.setPrefWidth(150);
        playsField.setPrefWidth(150);
        timeField.setPrefWidth(150);

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

        getChildren().addAll(nameField, mediaPathField, genreField, playsField, timeField, saveButton, cancelButton);
    }

    public Track getTrack() {
        track.setName(nameField.getText());
        track.setMediaPath(mediaPathField.getText());
        track.setGenre(genreField.getText());
        track.setPlays(Integer.parseInt(playsField.getText()));
        track.setTime(timeField.getText());
        return track;
    }

    public boolean validatePlaysField() {
        boolean isValid = false;

        return isValid;
    }

    public boolean validateTimeField() {
        boolean isValid = false;

        return isValid;
    }

    public void save() {
        DatabaseManager databaseManager = null;
        try {
            databaseManager = new DatabaseManager();
            databaseManager.updateTrack(getTrack());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetChildren() {
        nameField.setText(track.getName());
        mediaPathField.setText(track.getMediaPath());
        genreField.setText(track.getGenre());
        playsField.setText(track.getNumPlays()+"");
        timeField.setText(track.getTime());
    }
}
