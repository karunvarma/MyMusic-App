package MyMusic;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class TrackEditBox extends HBox {
    private TextField nameField;
    private TextField mediaPathField;
    private TextField genreField;
    private TextField playsField;
    private TextField timeField;
    private Button saveButton;
    private Button cancelButton;
    private Button deleteButton;

    private Track track;
    private boolean isNew;

    public TrackEditBox() {
        isNew = true;
        this.track = new Track();
        nameField = new TextField();
        mediaPathField = new TextField();
        genreField = new TextField();
        playsField = new TextField();
        timeField = new TextField();

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        mediaPathField.setPrefWidth(400);
        genreField.setPrefWidth(150);
        playsField.setPrefWidth(150);
        timeField.setPrefWidth(150);

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

        getChildren().addAll(nameField, mediaPathField, genreField, playsField, timeField, saveButton, cancelButton, deleteButton);
    }

    public TrackEditBox(Track track) {
        isNew = false;
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

        getChildren().addAll(nameField, mediaPathField, genreField, playsField, timeField, saveButton, cancelButton, deleteButton);
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
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        if (isNew) {
            try {
                databaseManager.addTrack(getTrack());
                isNew = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                databaseManager.updateTrack(getTrack());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void resetChildren() {
        if (isNew) {
            nameField.setText("");
            mediaPathField.setText("");
            genreField.setText("");
            playsField.setText("");
            timeField.setText("");
        }
        else {
            nameField.setText(track.getName());
            mediaPathField.setText(track.getMediaPath());
            genreField.setText(track.getGenre());
            playsField.setText(track.getNumPlays() + "");
            timeField.setText(track.getTime());
        }
    }

    private void delete() {
        if (isNew) {
            ((VBox) getParent()).getChildren().remove(this);
        }
        else {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            try {
                databaseManager.deleteTrack(getTrack());
                ((VBox) getParent()).getChildren().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
