package Final;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;

public class Minesweeper extends Application {
    private static Game game;
    private static VBox gameContainer;
    private static Scene scene;
    private static Stage stage;

    public void start(Stage primaryStage) throws Exception {
        game = new Game(Grid.Level.EASY);
        gameContainer = new VBox(game.render());
        gameContainer.setAlignment(Pos.CENTER);
        stage = primaryStage;
        // grid = new Grid(Grid.Level.EASY);
        // gameContainer = new VBox(grid.render());
        scene = new Scene(gameContainer, 400, 350);
        stage.setScene(scene);
        stage.show();
    }

    // write the current state of the game to the file
    public static void saveGame() {

    }

    // read in a game from a file
    public static void loadGame(String path) {

    }

    public static Grid getGrid() {
        return game.getGrid();
    }

    public static void setGame(Game newGame) {
        Minesweeper.game = newGame;
        gameContainer = new VBox(game.render());
    }

    public static Game getGame() {
        return game;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
