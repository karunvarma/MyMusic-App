package MyMusic;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrackTableView extends TableView {
    public TrackTableView(List<Track> trackList, User user) {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setPrefWidth(1380);


        // Instantiate table columns

        TableColumn playCol = new TableColumn("Play");
        playCol.setPrefWidth(50);
        playCol.setCellValueFactory(new PropertyValueFactory<>("PlayBtn"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn artistCol = new TableColumn("Artist");
        artistCol.setPrefWidth(200);
        artistCol.setCellValueFactory(new PropertyValueFactory<>("ArtistName"));

        TableColumn albumCol = new TableColumn("Album");
        albumCol.setPrefWidth(200);
        albumCol.setCellValueFactory(new PropertyValueFactory<>("AlbumName"));

        TableColumn genreCol = new TableColumn("Genre");
        genreCol.setPrefWidth(100);
        genreCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        TableColumn playCountCol = new TableColumn("Plays");
        playCountCol.setPrefWidth(100);
        playCountCol.setCellValueFactory(new PropertyValueFactory<>("NumPlays"));

        TableColumn yoursCol = new TableColumn("Yours");
        yoursCol.setPrefWidth(100);
        yoursCol.setCellValueFactory(new PropertyValueFactory<>("YoursCheckBox"));


        // Add table columns
        getColumns().addAll(playCol, nameCol, timeCol, artistCol, albumCol, genreCol, playCountCol, yoursCol);


        for (int i = 0; i < trackList.size(); i++) {
            getItems().add(new TrackRowData(user, trackList.get(i)));
        }
    }

    public class PlayButton extends Button {
        public PlayButton() {
            setText("Play");
        }
    }
}
