/**
 * Program Name: Minesweeper
 * <p>
 * Date Completed: 12:03:08AM, Sunday, May 21, 2023
 * <p>
 * Description of Program: "Minesweeper" creates a version of the popular online game using JavaFX. In the game, players identify locations on a grid where a "mine" does not exist. If the player selects a mine, then the game is over.
 * <p>
 * Revealed buttons on the grid will depict how many mines they are immediately adjacent to. Players may choose to flag locations where there is a mine by right clicking the space.
 * <p>
 * This program includes a save/load game function, as well as an instructions manual, sound effects, and three levels ("Easy", "Medium", and "Hard").
 * <p>
 * HONOR CODE PLEDGE
 * We agree to abide by the Academic Honesty Agreement signed at the beginning of class.a
 * Signed, Jacob Van Meter & Melody Lee
 * <p>
 * @author Jacob Van Meter
 * @author Melody Lee
 * @since 1.0
 */

package Final;

// import javafx packages
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;

// import java packages
import java.util.*;
import java.nio.file.*;


/**
 * The "main" function equivalent of this program. Creates the Minesweeper game and organizes the elements prior to display. 
 */
public class Minesweeper extends Application {
    private static Game game; // saves the current Game
    private static VBox gameContainer; // organizes the Game 
    private static Scene scene; // current scene
    private static Stage stage; // main game window

    // buttons to be used to trigger the Save game function
    private static Button saveGame = new Button("Save Game");
    // button to be used to trigger the Load game function
    private static Button loadGame = new Button("Load Game");

    // path of file to save and load the game from 
    private static String saveFilePath = "Final/gameArchive/save.txt";

    /**
     * Run when the application is started, loading the initial game and setting the stage for user interaction. 
     * 
     * @param primaryStage the window through which the Minesweeper game will be viewed
     */
    public void start(Stage primaryStage) throws Exception {
        // create a new game
        // set the default level at EASY
        game = new Game(Grid.Level.EASY);

        // loads the game container
        gameContainer = new VBox(game.render());

        // set spacing and alignment for game container
        gameContainer.setSpacing(10);
        gameContainer.setAlignment(Pos.CENTER);

        // create save/load functions button container
        HBox gameControls = new HBox(50);
        gameControls.setAlignment(Pos.CENTER);

        // add save and load buttons to container
        gameControls.getChildren().addAll(saveGame, loadGame);
        gameContainer.getChildren().add(gameControls);

        // set the save and load button functions
        saveGame.setOnAction(e -> saveGameFile(saveFilePath));
        loadGame.setOnAction(e -> loadGameFile(saveFilePath));

        // set and display the stage for Minesweeper
        stage = primaryStage;
        scene = new Scene(gameContainer, 800, 800);
        scene.getStylesheets().add("Final/Minesweeper.css");
        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }

    /**
     * Saves the current state of the game to a text file.
     * 
     *  @param filePath path of file the current game will be saved to
     */
    public static void saveGameFile(String filePath) {
        // get the state of the current game
        Grid g = game.getGrid();
        ArrayList<Block> blockCollection = g.getBlockCollection();

        // add characteristics of the game (e.g. moves made, number of mines, length)
        String out = ""; // this string is written into the save file
        out += g.getLevel().getLength();
        out += "\n" + game.getMovesMade();
        out += "\n" + game.getNumberMines();

        // add in the state of every block in column major order
        for (int i = 0; i < blockCollection.size();i++) {
            // write to file block characteristics
            Block b = blockCollection.get(i);
            out += "\n" + (b.getIsMine() ? 1 : 0); // if is mine, write 1, write 0 otherwise
            out += "\t" + (b.isRevealed() ? 1 : 0); // if is revealed, write 1, write 0 otherwise
            out += "\t" + b.getAdjacentMines(); 
        }

        // write to the file specified by filePath parameter
        try {
            Path path = Paths.get(filePath);
            Files.writeString(path, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game from the file specified in the file path and displays it as the current game.
     * 
     * @param filePath path of file the game will be loaded in from
     */
    public static void loadGameFile(String filePath) {
        try {
            // read in the lines from the specified file
            Path p = Paths.get(filePath);
            List<String> lines = Files.readAllLines(p);

            // create empty storage objects for the game
            ArrayList<Block> blockCollection = new ArrayList<>();
            GridPane grid = new GridPane();

            // get the characteristics of the game (e.g. length/level, moves made, number of mines)
            int length = Integer.parseInt(lines.get(0));
            Grid.Level level = length == 10 ? Grid.Level.EASY : length == 18 ? Grid.Level.MEDIUM : Grid.Level.HARD;
            int movesMade = Integer.parseInt(lines.get(1));
            int mines = Integer.parseInt(lines.get(2));

            // for every block, read in the information in column major order
            for (int c = 0; c < length; c++) {
                for (int r = 0; r < length; r++) {
                    // parse block characteristics
                    String[] blockInfo = lines.get(c * length + r + 3).split("\t");
                    boolean isMine = Integer.parseInt(blockInfo[0]) == 1;
                    boolean isRevealed = Integer.parseInt(blockInfo[1]) == 1;
                        
                    int adjacentMines = Integer.parseInt(blockInfo[2]);

                    // create new block with characteristics
                    Block block = new Block(isMine, isRevealed, adjacentMines, null, level);
                    
                    // set location of block
                    block.setColumn(c);
                    block.setRow(r);
                    
                    // add block to the game
                    blockCollection.add(block);
                    grid.add(block.render(), c, r);
                }
            }

            // replace existing game with parsed information
            game.loadGame(level, blockCollection, grid, movesMade, mines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the Grid object of the current Minesweeper game.
     * 
     * @return Grid of current game
     */
    public static Grid getGrid() {
        return game.getGrid();
    }

    /**
     * Sets the current game to the input. 
     * 
     * @param newGame Game to be set as the current game
     */
    public static void setGame(Game newGame) {
        Minesweeper.game = newGame;
        Minesweeper.gameContainer = new VBox(game.render());
    }

    /**
     * Gets the Game object of the current Minesweeper game.
     * 
     * @return Game of current game
     */
    public static Game getGame() {
        return game;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
