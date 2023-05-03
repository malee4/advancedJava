package Unit5.GoFish;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import java.util.*;

public class Grid {

    private VBox grid;

    public Grid(String dirPath, int number_cards) {
        // load in the cards
        ArrayList<Card> cards = new ArrayList<>();
        
        for (int i = 2; i <= number_cards + 1; i++) {
            cards.add(new Card(dirPath + i + ".png"));
        }
        
        // shuffle the cards
        Collections.shuffle(cards);

        // lay out the cards
        int length = (int) Math.sqrt(number_cards);
        VBox grid = new VBox(10);
        grid.setAlignment(Pos.CENTER);
        for (int i = 0; i < length; i++) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            for (int j = 0; j < length; j++) {
                row.getChildren().add(cards.get(i * length + j).getCard());
            }
            grid.getChildren().add(row);
        }

        this.grid = grid;
    }

    public VBox getGrid() {
        return this.grid;
    }
    
}
