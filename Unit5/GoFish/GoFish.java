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

public class GoFish extends Application {
    public static final int IMG_WIDTH = 100;
    public static final int IMG_HEIGHT = 75;

    public static final int NUMBER_CARDS = 36;
    public static final String PATH = "Unit5/GoFish/img/";
    public static VBox grid;
    public static VBox layout;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GoFish");

        grid = new Grid(PATH, NUMBER_CARDS).getGrid();

        layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);

        Button btn = new Button("Reset");
        btn.setAlignment(Pos.CENTER);
        btn.setOnAction(e->{
            grid = new Grid(PATH, NUMBER_CARDS).getGrid();
            layout.getChildren().clear();
            layout.getChildren().addAll(grid, btn);
        });

        layout.getChildren().addAll(grid, btn);

        Scene scn = new Scene(layout, 700, 750);
        primaryStage.setScene(scn);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
