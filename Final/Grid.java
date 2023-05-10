package Final;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.*;
import java.util.function.BiFunction;

public class Grid implements UIElement {
    private final int length;
    private final GridPane grid;
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

    public void generateNew() throws Exception {
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
                b.setAdjacentMines(1);
                blockCollection.add(b);
                grid.add(b.render(), c, r); // column major order
            }
        }

        // Set adjacent mines
        BiFunction<Integer, Integer, Block> getBlock = (column, row) -> blockCollection.get(column * length + row);
        BiFunction<Integer, Integer, Integer> isMine = (column, row) -> getBlock(row, column).getIsMine() ? 1 : 0;
        for (int c = 0; c < length; c++) {
            for (int r = 0; r < length; r++) {
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

                    getBlock.apply(c, r).setAdjacentMines(adjacentMineCount);
                }
            }
        }
    }
    
    public Grid(Level type) throws Exception {
        this.length = type.getLength();
        this.p = type.getProbability();
        this.level = type;

        // generate the grid of blocks
        grid = new GridPane();

        generateNew();
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

    public Block getBlock(int r, int c) {
        int index = c * length + r;
        return blockCollection.get(index);
    }

    @Override
    public Node render() {
        return grid;
    }

}
