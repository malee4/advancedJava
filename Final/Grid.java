package Final;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Represents the grid of blocks in the game
 * Handles the initialization of and keeps track of whats happening in the game with each block and its state
 */
public class Grid implements UIElement {
    private int length; // Grid size
    private GridPane grid; // GridPane
    private final double p; // probability of having a mine on the grid
    private Level level; // Difficulty
    private int minesRemaining = 0; // Number of mines remaining
    
    private ArrayList<Block> blockCollection = new ArrayList<>(); // List of blocks

    // Level of diffuclty enum
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
        
        /**
         * @return The grid size for each difficulty level
         */
        public int getLength() {
            switch (this) {
                case HARD:
                    return 20;
                case MEDIUM:
                    return 18;
                case EASY:
                    return 10;
                default:
                    return 10;
            }
        }
        
        /**
         * @return The probability of a block being a mine for each difficulty level
         */
        public double getProbability() {
            switch(this) {
                case HARD:
                    return 90.0 / 400.0;
                case MEDIUM: 
                    return 43.0 / 324.0;
                default:
                    return 13.0 / 100.0;
            }
        }
    }

    // Function to be run when a mine is revealed
    // Checks if the game should end, and if a region should be revealed
    public TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal = (wasMine, adjacentMines, location) -> {
        if (wasMine) {
            Minesweeper.getGame().gameOver();
        } else if (adjacentMines == 0)
            revealRegion(location.getKey(), location.getValue());

        return null;
    };

    /**
     * Generates a new grid of blocks
     * 
     * @param level Difficulty level
     * @throws Exception if a block is not initalized correctly (number of adjacent mines hasn't been set)
     */
    public void generateNew(Level level) throws Exception {
        // clear existing storage
        grid.getChildren().clear();
        this.blockCollection.clear();
        this.minesRemaining = 0;
        this.length = level.getLength();

        // Create new block for each position in the grid
        for (int c = 0; c < length; c++) {
            for (int r = 0; r < length; r++) {
                boolean isMine = false;
                if (Math.random() <= p) {
                    isMine = true;
                    minesRemaining++;
                }

                Block b = new Block(isMine, onReveal, level);
                b.setAdjacentMines(-1);

                // so the block "knows where it is"
                b.setColumn(c);
                b.setRow(r);
                
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

    /**
     * Calculate the number of mines that are adjacent to the block at the given location, and call the mine's setAdjacentMines method accordingly
     * This function is so long because of the number of edge cases it has to check
     * 
     * @param c Column index of block to check
     * @param r Row index of block to check
     */
    public void calculateAdjacentMines(int c, int r) {
        // Helper function to check if the block at a certain location is a mine or not
        // Returns 1 if it is, 0 if not
        // It returns an integer so we can easily count the number of adjacent mines
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

        // Left column
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

        // Right column
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

        // Bottom row
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
    
    /**
     * Constructs a Grid from scratch for a given difficulty
     * Used when generating a new game
     * 
     * @param level Level of difficulty
     * @throws Exception if there is an error initalizing the blocks
     */
    public Grid(Level level) throws Exception {
        this.length = level.getLength();
        this.p = level.getProbability();
        this.level = level;

        // generate the grid of blocks
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        generateNew(level);
    }

    /**
     * Constructs a Grid from existing game information
     * Used when loading a saved game
     * 
     * @param level Level of difficulty
     * @param grid Prior GridPane object
     * @param blockCollection List of blocks
     * @param mines Number of mines in the prior game
     */
    public Grid(Level level, GridPane grid, ArrayList<Block> blockCollection, int mines) {
        this.length = level.getLength();
        this.p = level.getProbability();
        this.level = level;
        this.minesRemaining = mines;

        this.grid = grid;
        grid.setAlignment(Pos.CENTER);

        this.blockCollection = blockCollection;   

        // Set onReveal for each block
        for (Block block : blockCollection) {
            block.setOnReveal(onReveal);
        }     
    }

    /**
     * @return Number of mines remaining
     */
    public int getMinesRemaining() {
        return minesRemaining;
    }

    /**
     * @return The length of the grid in blocks
     */
    public int getLength() {
        return length;
    }

    /**
     * @return The grid's corresponding level of difficulty
     */
    public Grid.Level getLevel() {
        return level;
    }

    /**
     * Sets the board's level of difficulty
     * Regenerates the level if a different difficulty than the current one is selected
     * 
     * @param newLevel New level of difficulty
     * @throws Exception if there is an error initializing the blocks
     */
    public void setLevel(Level newLevel) throws Exception {
        if (this.level.equals(newLevel)) {
            return;
        }

        this.level = newLevel;
        this.length = level.getLength();
        generateNew(newLevel);
    }

    /**
     * Gets the block at a certain location on the grid
     * 
     * @param c Column index of the block
     * @param r Row index of the block
     * @return The Block present at the given location
     */
    public Block getBlock(int c, int r) {
        int index = c * length + r;
        return blockCollection.get(index);
    }

    /**
     * Uses DFS to reveal groups of regions that are NOT adjacent to mines starting at a given block
     * 
     * @param c Column index of starting block
     * @param r Row index of starting block
     */
    public void revealRegion(int c, int r) {
        // Helper function to determine if revealRegion should continue to search on a given block
        // Checks that the block hasn't been revealed, isn't a mine, and is not adjacent to any mines
        BiFunction<Integer, Integer, Boolean> isNotRevealedAndNotMineAndNotAdjacentToMines = (column, row) -> {
            Block block = getBlock(column, row);
            return !block.isRevealed() && !block.getIsMine() && (block.getAdjacentMines() == 0);
        };
        
        // Helper function to reveal a block if it is on the border of a region that is not adjacent to mines
        BiFunction<Integer, Integer, Void> revealBorderBlock = (column, row) -> {
          Block block = getBlock(column, row);

          if (!block.isRevealed() && !(block.getAdjacentMines() == 0))
            block.reveal();

          return null;
        };
        
        // Helper function to reveal any adjacent mines that are on the border of a region that is not adjacent to mines
        BiFunction<Integer, Integer, Void> revealAdjacentBorderBlocks = (column, row) -> {
             // inner blocks
            if ((column > 0 && column < length - 1) && (row > 0 && row < length - 1)) {              
              // row above
              revealBorderBlock.apply(column - 1, row - 1);
              revealBorderBlock.apply(column + 0, row - 1);
              revealBorderBlock.apply(column + 1, row - 1);

              // row current
              revealBorderBlock.apply(column - 1, row + 0);
              revealBorderBlock.apply(column + 1, row + 0);

              // row below
              revealBorderBlock.apply(column - 1, row + 1);
              revealBorderBlock.apply(column + 0, row + 1);
              revealBorderBlock.apply(column + 1, row + 1);
          } else if (column == 0 && row == 0) {
              // row current
              revealBorderBlock.apply(column + 1, row + 0);

              // row below
              revealBorderBlock.apply(column + 0, row + 1);
              revealBorderBlock.apply(column + 1, row + 1);
          } else if (column == length - 1 && row == 0) {
              // row current
              revealBorderBlock.apply(column - 1, row + 0);

              // row below
              revealBorderBlock.apply(column + 0, row + 1);
              revealBorderBlock.apply(column - 1, row + 1);
          } else if (column == length - 1 && row == length - 1) {
              // row current
              revealBorderBlock.apply(column - 1, row + 0);

              // row above
              revealBorderBlock.apply(column + 0, row - 1);
              revealBorderBlock.apply(column - 1, row - 1);

              // lowerow left corner
          } else if (column == 0 && row == length - 1) {
              // row current
              revealBorderBlock.apply(column + 1, row+ 0);

              // row above
              revealBorderBlock.apply(column + 0, row- 1);
              revealBorderBlock.apply(column + 1, row- 1);

              // Left column
          } else if (column == 0) {
              // row above
              revealBorderBlock.apply(column + 0, row- 1);
              revealBorderBlock.apply(column + 1, row - 1);

              // row current
              revealBorderBlock.apply(column + 1, row + 0);
              
              // row below
              revealBorderBlock.apply(column + 0, row + 1);
              revealBorderBlock.apply(column + 1, row + 1);

              // Right column
          } else if (column == length - 1) {
              // row above
              revealBorderBlock.apply(column + 0, row - 1);
              revealBorderBlock.apply(column - 1, row - 1);

              // row current
              revealBorderBlock.apply(column - 1, row + 0);
              
              // row below
              revealBorderBlock.apply(column + 0, row + 1);
              revealBorderBlock.apply(column - 1, row + 1);

              // Top row
          } else if (row == 0) {
              // column to the left
              revealBorderBlock.apply(column - 1, row + 0);
              revealBorderBlock.apply(column - 1, row + 1);

              // column current
              revealBorderBlock.apply(column + 0, row + 1);
              
              // column to the right
              revealBorderBlock.apply(column + 1, row + 0);
              revealBorderBlock.apply(column + 1, row + 1);

              // Bottom row
          } else if (row == length - 1) {
              // column to the left
              revealBorderBlock.apply(column - 1, row + 0);
              revealBorderBlock.apply(column - 1, row - 1);

              // column current
              revealBorderBlock.apply(column + 0, row - 1);
              
              // column to the right
              revealBorderBlock.apply(column + 1, row + 0);
              revealBorderBlock.apply(column + 1, row - 1);
          } 
            return null;
        };
        
        // Reveal adjacent border blocks if there is one bordering the block that was just clicked
        revealAdjacentBorderBlocks.apply(c, r);
        

        // Check if each adjacent block (not including diagonals) should be revealed

        // up
        if ((r > 0) && isNotRevealedAndNotMineAndNotAdjacentToMines.apply(c, r - 1)) {
            Block block = getBlock(c, r - 1);
            
            // reveal block and rerun the revealRegion function on it, and reveal any adjacent border blocks
            block.reveal();
            revealRegion(c, r - 1);
            revealAdjacentBorderBlocks.apply(c, r - 1);
        }

        // down
        if ((r < length - 1) && isNotRevealedAndNotMineAndNotAdjacentToMines.apply(c, r + 1)) {
            Block block = getBlock(c, r + 1);
            
            // reveal block and rerun the revealRegion function on it, and reveal any adjacent border blocks
            block.reveal();
            revealRegion(c, r + 1);
            revealAdjacentBorderBlocks.apply(c, r + 1);
        }

        // left
        if ((c > 0) && isNotRevealedAndNotMineAndNotAdjacentToMines.apply(c - 1, r)) {
            Block block = getBlock(c - 1, r);
            
            // reveal block and rerun the revealRegion function on it, and reveal any adjacent border blocks
            block.reveal();
            revealRegion(c - 1, r);
            revealAdjacentBorderBlocks.apply(c - 1, r);
        }

        // right
        if ((c < length - 1) && isNotRevealedAndNotMineAndNotAdjacentToMines.apply(c + 1, r)) {
            Block block = getBlock(c + 1, r);
            
            // reveal block and rerun the revealRegion function on it, and reveal any adjacent border blocks
            block.reveal();
            revealRegion(c + 1, r);
            revealAdjacentBorderBlocks.apply(c + 1, r);
        }
        
        return;
    }

    /**
     * @return List of blocks in grid
     */
    public ArrayList<Block> getBlockCollection() {
        return blockCollection;
    }

    @Override
    public Node render() {
        return grid;
    }

}
