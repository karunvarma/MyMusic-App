package MyMusic;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.ArrayList;

public class SearchLayout extends HBox {

    SearchLayout() {
        // Children


        // Add children
        getChildren().addAll(new SearchLayoutLeft(), new SearchLayoutRight());
    }


    private class SearchLayoutLeft extends VBox {

        SearchLayoutLeft() {
            // Children
            Label label = new Label("Search Criteria");
            CheckBox checkBox_Track = new CheckBox("Track");
            CheckBox checkBox_Album = new CheckBox("Album");
            CheckBox checkBox_Artist = new CheckBox("Artist");


            VBox genreVBox = new VBox();
            ArrayList<String> genres = new ArrayList<String>();
            genres.add("Classical");
            genres.add("Country");
            genres.add("Pop");
            genres.add("Rock");
            genres.add("Soul");
            genres.add("Soundtrack");

            for (int i = 0; i < genres.size(); i++) {
                CheckBox checkBox = new CheckBox(genres.get(i));
                genreVBox.getChildren().add(checkBox);
            }
            TitledPane titledPane_Genre = new TitledPane("Genre", genreVBox);


            // Dimensions
            setPrefWidth(Screen.getPrimary().getBounds().getMaxX() / 5);
            setPrefHeight(Screen.getPrimary().getBounds().getMaxY());


            // Set style
            setStyle("-fx-border-style: solid"
            );

            // Add children
            getChildren().addAll(label, checkBox_Track, checkBox_Album, checkBox_Artist, titledPane_Genre);
        }
    }



    private class SearchLayoutRight extends VBox {

        SearchLayoutRight() {
            TextField searchTextField = new TextField();
            searchTextField.setMaxWidth(Screen.getPrimary().getBounds().getMaxX() / 2);
            searchTextField.setMinHeight(50);
            searchTextField.setMinWidth(Screen.getPrimary().getBounds().getMaxX() - Screen.getPrimary().getBounds().getMaxX() / 5 - 140);
            searchTextField.setId("search");

            Button searchBtn = new Button("Search");
            searchBtn.setMinHeight(50);
            searchBtn.setMinWidth(100);

            HBox searchHBox = new HBox();
            searchHBox.getChildren().addAll(searchTextField, searchBtn);
            searchHBox.setStyle("-fx-alignment: center;" + "-fx-padding: 20;");



            // Dimensions
            setPrefWidth(Screen.getPrimary().getBounds().getMaxX() - Screen.getPrimary().getBounds().getMaxX() / 5);
            setPrefHeight(Screen.getPrimary().getBounds().getMaxY());


            // Set style
            setStyle("-fx-border-style: solid;");

            // Add children
            getChildren().addAll(searchHBox, new SearchResultsVBox());

        }
    }



    private class SearchResultsVBox extends VBox {
        SearchResultsVBox() {

        }
    }
}