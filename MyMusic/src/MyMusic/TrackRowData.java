package MyMusic;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class TrackRowData {
    User user;
    Track track;
    private Button playBtn;
    private CheckBox yoursCheckBox;


    public TrackRowData(User user, Track track) {
        this.user = user;
        this.track = track;
        playBtn = new Button("Play");
        yoursCheckBox = new CheckBox();
        yoursCheckBox.setSelected(track.isYours());

        playBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                track.play();
            }
        });

        yoursCheckBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (track.isYours()) {
                    try {
                        new DatabaseManager().deleteUser_has_Track(user, track);
                        track.setIsYours(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        new DatabaseManager().addUser_has_Track(user, track);
                        track.setIsYours(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public int getId() { return track.getId(); }

    public String getName()
    {
        return track.getName();
    }

    public int getAlbumId() { return track.getAlbumId(); }

    public String getTime()
    {
        return track.getTime();
    }

    public int getNumPlays()
    {
        return track.getNumPlays();
    }

    public String getArtistName()
    {
        return track.getArtistName();
    }

    public String getAlbumName()
    {
        return track.getAlbumName();
    }

    public String getGenre()
    {
        return track.getGenre();
    }

    public String getMediaPath()
    {
        return track.getMediaPath();
    }

    public Button getPlayBtn() { return playBtn; }
    public CheckBox getYoursCheckBox() { return yoursCheckBox; }
}
