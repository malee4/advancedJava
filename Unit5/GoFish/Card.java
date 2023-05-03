package Unit5.GoFish;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

import java.io.*;

public class Card {

    private boolean flipped;

    private ImageView img;
    private ImageView blank;
    private ImageView curr;

    private VBox card;
    private String imgPath;
    private boolean showingCard;

    public Card(String imgPath) {
        this.imgPath = imgPath;
        this.showingCard = false;
        try {
            // load in the initial image
            FileInputStream input = new FileInputStream(imgPath);
            Image image = new Image(input);
            this.img = new ImageView(image);

            // load in the blank file
            input = new FileInputStream("./Unit5/GoFish/img/blank.png");
            this.blank = new ImageView(new Image(input));
            this.curr = this.blank;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btn = new Button("Flip Card");
        btn.setAlignment(Pos.CENTER);
        btn.setOnAction(e -> {
            this.showingCard = !this.showingCard;
            if (this.showingCard) {
                this.curr = this.img;
            } else this.curr = this.blank;

            this.card.getChildren().clear();
            this.card.getChildren().addAll(this.curr, btn);
        });

        // create the layout of the card
        this.card = new VBox();
        card.getChildren().addAll(this.curr, btn);
    }

    public ImageView getImage() {
        return this.img;
    }

    public ImageView getBlank() {
        return this.img;
    }

    public ImageView getcurr() {
        return this.curr;
    }

    public boolean isShowing() {
        return this.showingCard;
    }

    public VBox getCard() {
        return this.card;
    }
}
