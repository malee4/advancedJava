package Final;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Grid implements UIElement {
    private final int length;
    private final GridPane grid;
    private final double p; // probability of having a mine on the grid
    private int minesRemaining = 0;

    public enum Level {
        EASY, 
        MEDIUM, 
        HARD;

        @Override
        public String toString() {
            switch(this) {
                case EASY:
                    return "Easy";
                case MEDIUM:
                    return "Medium";
                case HARD:
                    return "Hard";
                default:
                    return "";
            }
        }

        public int getLength() {
            switch (this) {
                case HARD:
                    return 24;
                case MEDIUM:
                    return 18;
                default:
                    return 10;
            }
        }
    }
    
    public Grid(int length, double p) throws Exception {
        this.length = length;
        this.p = p;

        // generate the grid of blocks
        grid = new GridPane();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                boolean isMine = false;
                if (Math.random() <= p) {
                    isMine = true;
                    minesRemaining++;
                }

                Block b = new Block(isMine);
                grid.add(b.render(), i, j);
            }
        }
    }

    public int getMinesRemaining() {
        return minesRemaining;
    }

    public int getLength() {
        return length;
    }

    @Override
    public Node render() {
        return grid;
    }

}
