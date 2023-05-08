package Final;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Game implements UIElement {
    private VBox container = new VBox(10);
    private Grid grid;


    public Game(Grid.Level level) throws Exception {
        // create the grid
        grid = new Grid(level);

        
    }

    // check to see if the the game is over
    public void gameOver(int row, int column) {
        // check to see if a mine has been hit
    }

    @Override 
    public Node render() {
        return container;
    }
}
