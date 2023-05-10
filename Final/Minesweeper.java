package Final;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;

public class Minesweeper extends Application {
    private static Game game;
    private static VBox gameContainer;

    public void start(Stage primaryStage) throws Exception {
        game = new Game(Grid.Level.EASY);
        gameContainer = new VBox(game.render());
        // grid = new Grid(Grid.Level.EASY);
        // gameContainer = new VBox(grid.render());

        Scene scene = new Scene(gameContainer, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Grid getGrid() {
        return game.getGrid();
    }

    public static Game getGame() {
        return game;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
