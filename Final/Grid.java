package Final;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Grid implements UIElement {
    private final int length;
    private final GridPane grid;
    private final double p; // probability of having a mine on the grid
    private Level level;
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

        public double getProbability() {
            switch(this) {
                case HARD:
                    return 99.0 / 576.0;
                case MEDIUM: 
                    return 40.0 / 324.0;
                default:
                    return 10.0 / 100.0;
            }
        }
    }
    
    public Grid(Level type) throws Exception {
        this.length = type.getLength();
        this.p = type.getProbability();
        this.level = type;

        // generate the grid of blocks
        grid = new GridPane();

        for (int c = 0; c < length; c++) {
            for (int r = 0; r < length; r++) {
                boolean isMine = false;
                if (Math.random() <= p) {
                    isMine = true;
                    minesRemaining++;
                }

                Block b = new Block(isMine);
                grid.add(b.render(), c, r);
            }
        }
    }

    public int getMinesRemaining() {
        return minesRemaining;
    }

    public int getLength() {
        return length;
    }

    public String getLevel() {
        return level.toString();
    }

    public void setLevel(Level newLevel) {
        this.level = newLevel;
    }

    @Override
    public Node render() {
        return grid;
    }

}
