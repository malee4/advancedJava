package Final;

// import javafx packages
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.media.*;
import javafx.geometry.Pos;

// import java packages
import java.io.File;
import java.util.*;

/**
 * Game is an instance of the Minesweeper game that is played, storing the player's Minesweeper grid, the state of each block, and general characteristics about the current game (such as the number of mines in the grid, the number of moves made/blocks revealed, and the level).
 */
public class Game implements UIElement {
    private VBox container = new VBox(10); // game container
    private Grid grid; // grid of Blocks

    private int movesMade = 0; // counts the number of blocks revealed
    private Label movesMadeLabel = new Label("0"); // displays the movesMade value
    private int numberMines = 0; // number of mines present in the grid

    private Button resetButton = new Button("Reset"); // creates button to clear the game
    private Menu menu = new Menu(); // creates a new intance of a Menu

    private static final String WON_SOUND_FILE_PATH = "Final/assets/winSound.wav"; // file path to the sound file played when the game is won

    /**
     * Creates a new game by building the Grid object and setting the number of mines. 
     * 
     * @param level the level of the game selected by the player (or the default)
     * @throws Exception if grid can not be properly created.
     */
    public Game(Grid.Level level) throws Exception {
        // create the grid
        grid = new Grid(level);
        numberMines = grid.getMinesRemaining();
        buildUi(grid);
    }

    /**
     * Builds the user interface for the game, including button functions
     * 
     * @param grid Grid associated with this Game
     */
    public void buildUi(Grid grid) {
        // set up the reset button
        resetButton.setAlignment(Pos.CENTER); 
        resetButton.setOnAction(e-> {
            try {
                reset();
            } catch (Exception ex){
                ex.printStackTrace();
            } 
        });

        // set up the help button
        Button help = new Button("?");
        help.setOnAction(e-> {
            // display the Instructions window when button is pressed
            Instructions.display();
        });

        // add and organize the UI elements in the container
        HBox bottomMenu = new HBox(30);
        bottomMenu.getChildren().addAll(movesMadeLabel, resetButton, help);
        bottomMenu.setAlignment(Pos.CENTER);

        container.getChildren().clear();
        container.getChildren().addAll(menu.getHorizontal(), grid.render(), bottomMenu);
        container.setAlignment(Pos.CENTER);
    }

    /**
     * Checks to see if the game has been won. If it has been won, the game is ended and the player has the option to reset the game. 
     */
    public void isGameWon() {
        // checks to see if all safe blocks have been revealed 
        if (numberMines + movesMade == (int) Math.pow(grid.getLength(), 2)) {
            // plays the game won sound
            Media sound = new Media(new File(WON_SOUND_FILE_PATH).toURI().toString());
            MediaPlayer wonSound = new MediaPlayer(sound);
            wonSound.play();

            // display the Game Won window
            GameOverWindow.display("Game won");
        }
            
    }

    /**
     * Displays the Game Over window
     */
    public void gameOver() {
        // display the Game Over window
        GameOverWindow.display("Game over");
        return;
    }

    /**
     * Sets the movesMade to the input value.
     * 
     * @param i new value for movesMade
     */
    public void setMovesMade(int i) {
        movesMade = i;
        movesMadeLabel.setText("" + movesMade); // update the text displayed on the label
    }

    /**
     * Gets the number of blocks revealed (or movesMade)
     * 
     * @return int number of blocks revealed
     */
    public int getMovesMade() {
        return movesMade;
    }

    /**
     * Starts a new game when called.
     * 
     * @throws Exception 
     */
    public void reset() throws Exception {
        grid.generateNew(Grid.Level.EASY); // create a new grid with the default value
        menu.getLevelSelector().setText("Easy");

        // reset existing values
        setMovesMade(0);
        numberMines = grid.getMinesRemaining();
        return;
    }

    /**
     * Start a new game with the information loaded via the function parameters. 
     * 
     * @param level Level of the new game
     * @param blocks ArrayList of all blocks, listed in column-major order
     * @param gp GridPane of all the buttons in the game
     * @param moves number of blocks revealed
     * @param mines number of mines in the grid
     */
    public void loadGame(Grid.Level level, ArrayList<Block> blocks, GridPane gp, int moves, int mines) throws Exception {
        // generates new grid at set level, and sets its blockCollection and other characteristics accordingly
        grid = new Grid(level, gp, blocks, mines);
        menu.getLevelSelector().setText(level.toString());
        numberMines = grid.getMinesRemaining();

        // sets the moves made
        setMovesMade(moves);

        // sets up the UI for the grid
        buildUi(grid);
    }

    /**
     * Gets the grid of the current game
     * 
     * @return Grid of the current game
     */
    public Grid getGrid() {
        return grid;
    }

     /**
     * Gets the number of mines in the grid of this Game
     * 
     * @return int the number of mines in the Game grid
     */
    public int getNumberMines() {
      return numberMines;
    }

    /**
     * Returns the game container with the grid and menus organized.
     * 
     * @return Node the game container with all elements
     */
    @Override 
    public Node render() {
        return container;
    }
}
