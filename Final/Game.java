package Final;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;
import javafx.scene.control.*;

import javafx.geometry.Pos;

public class Game implements UIElement {
    private VBox container = new VBox(10);
    private Grid grid;

    private int movesMade = 0;
    private Label movesMadeLabel = new Label("0");
    private int numberMines = 0;

    private Button resetButton = new Button("Reset");
    private Menu menu = new Menu();

    private static final String WON_SOUND_FILE_PATH = "Final/assets/winSound.wav";

    public Game(Grid.Level level) throws Exception {
        // create the grid
        grid = new Grid(level);
        numberMines = grid.getMinesRemaining();
        buildUi(grid);
    }

    public void buildUi(Grid grid) {
        resetButton.setAlignment(Pos.CENTER);
        resetButton.setOnAction(e-> {
            try {
                reset();
            } catch (Exception ex){
                ex.printStackTrace();
            } 
        });

        Button help = new Button("?");
        help.setOnAction(e-> {
            Instructions.display();
        });

        HBox bottomMenu = new HBox(30);
        bottomMenu.getChildren().addAll(movesMadeLabel, resetButton, help);
        bottomMenu.setAlignment(Pos.CENTER);

        container.getChildren().clear();
        container.getChildren().addAll(menu.getHorizontal(), grid.render(), bottomMenu);
        container.setAlignment(Pos.CENTER);
    }

    public void isGameWon() {
        if (numberMines + movesMade == (int) Math.pow(grid.getLength(), 2)) {
            Media sound = new Media(new File(WON_SOUND_FILE_PATH).toURI().toString());
            MediaPlayer wonSound = new MediaPlayer(sound);
            wonSound.play();
            GameOverWindow.display("Game won");
        }
            
    }

    // when game over, will open screen indicating the game has ended
    // screen will contain "reset" button
    public void gameOver() {
        System.out.println("Game over");
        GameOverWindow.display("Game over");
        return;
    }

    public void setMovesMade(int i) {
        movesMade = i;
        movesMadeLabel.setText("" + movesMade);
    }

    public int getMovesMade() {
        return movesMade;
    }

    // will start a new game
    public void reset() throws Exception {
        grid.generateNew(Grid.Level.EASY);
        menu.getLevelSelector().setText("Easy");
        setMovesMade(0);
        numberMines = grid.getMinesRemaining();
        return;
    }

    // will start a new game
    public void loadGame(Grid.Level level, ArrayList<Block> blocks, GridPane gp, int moves, int mines) throws Exception {
        // generates new grid at set level, and sets its blockCollection accordingly
        grid = new Grid(level, gp, blocks, mines);
        menu.getLevelSelector().setText(level.toString());
        numberMines = grid.getMinesRemaining();

        // sets the moves made
        setMovesMade(moves);

        buildUi(grid);
    }

    public Grid getGrid() {
        return grid;
    }
    
    @Override 
    public Node render() {
        return container;
    }

    public int getNumberMines() {
      return numberMines;
    }
}
