package Final;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Game implements UIElement {
    private VBox container = new VBox(10);
    private Grid grid;

    private int movesMade = 0;
    private Label movesTrackerText = new Label();


    public Game(Grid.Level level) throws Exception {
        // create the grid
        grid = new Grid(level);
        Menu menu = new Menu();

        container.getChildren().addAll(menu.getHorizontal(), grid.render());
    }

    // when game over, will open screen indicating the game has ended
    // screen will contain "reset" button
    public void gameOver() {
        System.out.println("Game over");
        return;
    }

    public void setMovesMade(int i) {
        movesMade = i;
        movesTrackerText.setText("" + movesMade);
    }

    public int getMovesMade() {
        return movesMade;
    }

    // will start a new game
    public void reset() throws Exception {
        // grid.generateNew();
        setMovesMade(0);
        return;
    }

    public Grid getGrid() {
        return grid;
    }
    
    @Override 
    public Node render() {
        return container;
    }
}
