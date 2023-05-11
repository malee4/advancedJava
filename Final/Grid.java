package Final;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.*;
import java.util.function.BiFunction;

public class Grid implements UIElement {
    private int length;
    private GridPane grid;
    private final double p; // probability of having a mine on the grid
    private Level level;
    private int minesRemaining = 0;

    private ArrayList<Block> blockCollection = new ArrayList<>();

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
                case EASY:
                    return 10;
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

    public void generateNew(Level level) throws Exception {
        // clear existing storage
        grid.getChildren().clear();
        this.blockCollection.clear();
        this.length = level.getLength();

        for (int c = 0; c < length; c++) {
            for (int r = 0; r < length; r++) {
                boolean isMine = false;
                if (Math.random() <= p) {
                    isMine = true;
                    minesRemaining++;
                }

                // Block b = new Block(isMine, wasMine -> System.out.println("Clicked mine?: " + wasMine), level);
                Block b = new Block(isMine, wasMine -> {
                    System.out.println("Clicked mine?: " + wasMine); // for tracking purposes
                    Minesweeper.getGame().setMovesMade(Minesweeper.getGame().getMovesMade() + 1); // update the moves counter
                    if (wasMine) Minesweeper.getGame().gameOver();
                }, level);
                b.setAdjacentMines(-1);
                blockCollection.add(b);
                grid.add(b.render(), c, r); // column major order
            }
        }

        // Set adjacent mines
        for (int c = 0; c < length; c++) {
            for (int r = 0; r < length; r++) {
                calculateAdjacentMines(c, r);
            }
        }
    }

    public void calculateAdjacentMines(int c, int r) {
        BiFunction<Integer, Integer, Integer> isMine = (column, row) -> getBlock(column, row).getIsMine() ? 1 : 0;

        // inner blocks
        if ((c > 0 && c < length - 1) && (r > 0 && r < length - 1)) {
            int adjacentMineCount = 0;
            
            // row above
            adjacentMineCount += isMine.apply(c - 1, r - 1);
            adjacentMineCount += isMine.apply(c + 0, r - 1);
            adjacentMineCount += isMine.apply(c + 1, r - 1);

            // row current
            adjacentMineCount += isMine.apply(c - 1, r + 0);
            adjacentMineCount += isMine.apply(c + 1, r + 0);

            // row below
            adjacentMineCount += isMine.apply(c - 1, r + 1);
            adjacentMineCount += isMine.apply(c + 0, r + 1);
            adjacentMineCount += isMine.apply(c + 1, r + 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        // upper left corner
        } else if (c == 0 && r == 0) {
            int adjacentMineCount = 0;

            // row current
            adjacentMineCount += isMine.apply(c + 1, r + 0);

            // row below
            adjacentMineCount += isMine.apply(c + 0, r + 1);
            adjacentMineCount += isMine.apply(c + 1, r + 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        // upper right corner
        } else if (c == length - 1 && r == 0) {
            int adjacentMineCount = 0;

            // row current
            adjacentMineCount += isMine.apply(c - 1, r + 0);

            // row below
            adjacentMineCount += isMine.apply(c + 0, r + 1);
            adjacentMineCount += isMine.apply(c - 1, r + 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        // lower right corner
        } else if (c == length - 1 && r == length - 1) {
            int adjacentMineCount = 0;

            // row current
            adjacentMineCount += isMine.apply(c - 1, r + 0);

            // row above
            adjacentMineCount += isMine.apply(c + 0, r - 1);
            adjacentMineCount += isMine.apply(c - 1, r - 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        // lower left corner
        } else if (c == 0 && r == length - 1) {
            int adjacentMineCount = 0;

            // row current
            adjacentMineCount += isMine.apply(c + 1, r + 0);

            // row above
            adjacentMineCount += isMine.apply(c + 0, r - 1);
            adjacentMineCount += isMine.apply(c + 1, r - 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        } else if (c == 0) {
            int adjacentMineCount = 0; 

            // row above
            adjacentMineCount += isMine.apply(c + 0, r - 1);
            adjacentMineCount += isMine.apply(c + 1, r - 1);

            // row current
            adjacentMineCount += isMine.apply(c + 1, r + 0);
            
            // row below
            adjacentMineCount += isMine.apply(c + 0, r + 1);
            adjacentMineCount += isMine.apply(c + 1, r + 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        } else if (c == length - 1) {
            int adjacentMineCount = 0; 

            // row above
            adjacentMineCount += isMine.apply(c + 0, r - 1);
            adjacentMineCount += isMine.apply(c - 1, r - 1);

            // row current
            adjacentMineCount += isMine.apply(c - 1, r + 0);
            
            // row below
            adjacentMineCount += isMine.apply(c + 0, r + 1);
            adjacentMineCount += isMine.apply(c - 1, r + 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);

        // top row
        } else if (r == 0) {
            int adjacentMineCount = 0; 

            // column to the left
            adjacentMineCount += isMine.apply(c - 1, r + 0);
            adjacentMineCount += isMine.apply(c - 1, r + 1);

            // column current
            adjacentMineCount += isMine.apply(c + 0, r + 1);
            
            // column to the right
            adjacentMineCount += isMine.apply(c + 1, r + 0);
            adjacentMineCount += isMine.apply(c + 1, r + 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        } else {
            int adjacentMineCount = 0;

            // column to the left
            adjacentMineCount += isMine.apply(c - 1, r + 0);
            adjacentMineCount += isMine.apply(c - 1, r - 1);

            // column current
            adjacentMineCount += isMine.apply(c + 0, r - 1);
            
            // column to the right
            adjacentMineCount += isMine.apply(c + 1, r + 0);
            adjacentMineCount += isMine.apply(c + 1, r - 1);

            getBlock(c, r).setAdjacentMines(adjacentMineCount);
        }
    }

    // public void refreshAdjacentMineCounts(int c, int r) {
        
    // }
    
    public Grid(Level type) throws Exception {
        this.length = type.getLength();
        this.p = type.getProbability();
        this.level = type;

        // generate the grid of blocks
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        generateNew(type);
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

    public void setLevel(Level newLevel) throws Exception {
        if (this.level.equals(newLevel)) {
            System.out.println("same level");
            return;
        }

        this.level = newLevel;
        this.length = level.getLength();
        generateNew(newLevel);
    }

    public Block getBlock(int c, int r) {
        int index = c * length + r;
        return blockCollection.get(index);
    }

    @Override
    public Node render() {
        return grid;
    }

}
