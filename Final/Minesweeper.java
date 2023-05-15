package Final;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;

import javafx.application.Application;
import javafx.geometry.Pos;

import java.nio.file.*;
import java.io.*;

public class Minesweeper extends Application {
    private static Game game;
    private static VBox gameContainer;
    private static Scene scene;
    private static Stage stage;

    private static Button saveGame = new Button("Save Game");
    private static Button loadGame = new Button("Load Game");

    public void start(Stage primaryStage) throws Exception {
        game = new Game(Grid.Level.EASY);
        gameContainer = new VBox(game.render());
        gameContainer.setSpacing(10);

        gameContainer.setAlignment(Pos.CENTER);

        saveGame.setOnAction(e -> saveGame("Final/gameArchive/save.txt"));

        HBox gameControls = new HBox(50);
        gameControls.setAlignment(Pos.CENTER);

        gameControls.getChildren().addAll(saveGame, loadGame);
        gameContainer.getChildren().add(gameControls);

        stage = primaryStage;
        scene = new Scene(gameContainer, 800, 800);
        scene.getStylesheets().add("Final/Minesweeper.css");
        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }

    // write the current state of the game to the file
    // for now, will overwrite save files
    /*
     * File format: 
     * length
     * isMine (true/false)\tisRevealed (true/false)\tadjacentMines
     * 
     * Written in column major order
     */
    public static void saveGame(String filePath) {
        Grid g = game.getGrid();
        ArrayList<Block> blockCollection = g.getBlockCollection();

        // write the string
        String out = "" + g.getLevel().getLength();

        for (int i = 0; i < blockCollection.size();i++) {
            Block b = blockCollection.get(i);
            out += "\n" + (b.getIsMine() ? 1 : 0);
            out += "\t" + (b.isRevealed() ? 1 : 0);
            out += "\t" + b.getAdjacentMines();
        }

        try {
            Path path = Paths.get(filePath);
            Files.writeString(path, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // read in a game from a file
    public static Pair<ArrayList<Block>, GridPane> loadGame(String filePath, TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> handleClick) {
        try {
            Path p = Paths.get(filePath);

            List<String> lines = Files.readAllLines(p);

            ArrayList<Block> blockCollection = new ArrayList<>();
            GridPane grid = new GridPane();

            int length = Integer.parseInt(lines.get(0));

            for (int c = 0; c < length; c++) {
                for (int r = 0; r < length; r++) {
                    String[] blockInfo = lines.get(c * length + r).split(" ");
                    boolean isMine = Integer.parseInt(blockInfo[0]) == 1;
                    boolean isRevealed = Integer.parseInt(blockInfo[1]) == 1;
                    int adjacentMines = Integer.parseInt(blockInfo[2]);

                    Block block = new Block(isMine, handleClick, Grid.Level.EASY);
                    if (isRevealed) // uhhh this isn't going to work well
                        block.reveal();
                    block.setAdjacentMines(adjacentMines);

                    blockCollection.add(block);
                    grid.add(block.render(), c, r); // column major order
                }
            }

            return new Pair<ArrayList<Block>, GridPane>(blockCollection, grid);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
