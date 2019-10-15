package MyMusic;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class TopBar extends HBox {

        TopBar() {
            // Children
            MyMusicButton yourMusicBtn = new MyMusicButton("YourMusic");
            MyMusicButton findMusicBtn = new MyMusicButton("FindMusic");

            // Set spacing
            setSpacing(10);

            // Set style
            setStyle("-fx-padding: 5;"
            + "-fx-alignment: center;"
            );

            // Add children
            getChildren().addAll(yourMusicBtn, findMusicBtn);
        }


    private class MyMusicButton extends Button {

        MyMusicButton(String text) {
            super(text);

            // Dimensions
            setPrefWidth(150);
            setPrefHeight(50);

            // Set style
            setStyle("-fx-padding: 5;"
                    + "-fx-alignment: center;"
                    + "-fx-font-size: 24;"
                    + "-fx-background: none;"

            );
        }
    }
}