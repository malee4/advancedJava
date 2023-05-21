package Final;

// import javafx packages
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import javafx.scene.input.MouseButton;

// import java packages
import java.io.*;

/**
 * Represents a single "Block" in the minesweeper game.
 * A block could either be a mine or not a mine.
 * 
 * This class maintains the state and displays the user interface for a Block.
 * e.g. whether or not a block is revealed, displaying how many adjacent mines there are, etc.
 */
public class Block implements UIElement {
    private final Button blockButton; // Button object
    private final boolean isMine; // Whether or not the block is a mine or not

    // Called when the block is clicked on and revealed.
    // Callback is called with true if the block is a mine, false if it is not.
    private TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal;

    private boolean revealed = false; // Whether or not the block is revealed

    // Images to show on the block depending on it's state
    private ImageView hiddenImage;
    private ImageView revealedImage;
    private ImageView currentImage; 
    private ImageView flaggedImage;
    private final Label buttonLabel;

    private Integer adjacentMines; // Number of adjacent miens

    // so the block "knows where it is"
    private int c;
    private int r;

    // dimensions for image
    private static final int EASY_IMG_DIM = 15;
    private static final int MEDIUM_IMG_DIM = 10;
    private static final int HARD_IMG_DIM = 5;

    // Button dimension
    private static final int BUTTON_DIM = 30;

    // Sound file paths
    private static final String SAFE_SOUND_FILE_PATH = "Final/assets/safeSound.wav";
    private static final String MINE_SOUND_FILE_PATH = "Final/assets/mineSound.wav";
    private static final String FLAG_SOUND_FILE_PATH = "Final/assets/flagSound_2.wav";
    private static final MediaPlayer FLAG_SOUND = new MediaPlayer(new Media(new File(FLAG_SOUND_FILE_PATH).toURI().toString()));

    MediaPlayer player; // MediaPlayer

    /**
     * Constructs a new Block in an initial state
     * Used when generating a new game
     * @param isMine whether or not this block should be a mine
     * @param onReveal function to run when the block is revealed
     * @param level current game difficulty level
     */
    public Block(boolean isMine, TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal, Grid.Level level) {
      this.isMine = isMine;
      this.onReveal = onReveal;

      // Setup blockButton
      blockButton = new Button("");
      blockButton.getStyleClass().add("block");

      // Load images and sounds
      try {
        hiddenImage = new ImageView(new Image(new FileInputStream("./Final/assets/blank.png"))); // image shown when block is hidden
        flaggedImage = new ImageView(new Image(new FileInputStream("./Final/assets/flag.png"))); // import shown when a flag has been added
        
        if (isMine) {
          Media sound = new Media(new File(MINE_SOUND_FILE_PATH).toURI().toString());
          this.player = new MediaPlayer(sound);
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/mine.png")));
        }
        else {
          Media sound = new Media(new File(SAFE_SOUND_FILE_PATH).toURI().toString());
          this.player = new MediaPlayer(sound);
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/safe.png")));
        }
        
      } catch (IOException e) {
        System.out.println("Error loading block image assets: " + e);
        System.exit(0);
      }

      // Set initial image
      currentImage = new ImageView(hiddenImage.getImage());

      // adjust the dimensions of the image
      int imgDim = getImageDimensions(level);
      currentImage.setFitHeight(imgDim);
      currentImage.setFitWidth(imgDim);

      // Setup the current block label
      buttonLabel = new Label("");
      StackPane buttonPane = new StackPane(currentImage, buttonLabel);
      
      // Set the attributes of the Block button correctly
      blockButton.setGraphic(buttonPane);
      blockButton.setMinWidth(BUTTON_DIM);
      blockButton.setMaxWidth(BUTTON_DIM);
      blockButton.setMinHeight(BUTTON_DIM);
      blockButton.setMaxHeight(BUTTON_DIM);
      
      // Handle what happens when the block is clicked on
      blockButton.setOnMouseClicked(e -> {
        if (e.getButton() == MouseButton.PRIMARY) {
          player.play();
          reveal();
        }

        if (e.getButton() == MouseButton.SECONDARY){
          FLAG_SOUND.play();
          placeFlag();
        }
      });
    }

    /**
     * Constructs a new Block at a given state
     * Used when loading a previous game
     * @param isMine whether or not this block should be a mine
     * @param onReveal function to run when the block is revealed
     * @param adjacentMines number of mines adjacent to this block
     * @param level current game difficulty level
     */
    public Block(boolean isMine, boolean isRevealed, int adjacentMines, TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal, Grid.Level level) {
      this.isMine = isMine;
      this.onReveal = onReveal;
      this.revealed = isRevealed;
      this.adjacentMines = adjacentMines;
      
      // Setup blockButton
      blockButton = new Button("");
      blockButton.getStyleClass().add("block");

      // Load images and sounds
      try {
        hiddenImage = new ImageView(new Image(new FileInputStream("./Final/assets/blank.png")));// image shown when block is hidden
        flaggedImage = new ImageView(new Image(new FileInputStream("./Final/assets/flag.png"))); // import shown when a flag has been added
        
        if (isMine)
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/mine.png"))); // set image when revealed to be that of a mine
        else
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/safe.png"))); // set image when revealed to be that of a safe block
        
      } catch (IOException e) {
        System.out.println("Error loading block image assets: " + e);
        System.exit(0);
      }

      currentImage = new ImageView(revealed ? revealedImage.getImage() : hiddenImage.getImage());

      // adjust the dimensions of the image
      int imgDim = getImageDimensions(level);
      currentImage.setFitHeight(imgDim);
      currentImage.setFitWidth(imgDim);
      

      // Setup the current block label
      buttonLabel = new Label("");
      StackPane buttonPane = new StackPane(currentImage, buttonLabel);
      
      // Set the attributes of the Block button correctly
      blockButton.setGraphic(buttonPane);
      blockButton.setMinWidth(BUTTON_DIM);
      blockButton.setMaxWidth(BUTTON_DIM);
      blockButton.setMinHeight(BUTTON_DIM);
      blockButton.setMaxHeight(BUTTON_DIM);
      
      // Handle what happens when the block is clicked on
      blockButton.setOnMouseClicked(e -> {
        if (e.getButton() == MouseButton.PRIMARY) {
          reveal();
        }

        if (e.getButton() == MouseButton.SECONDARY){
          placeFlag();
        }
      });

      // If the block is already revealed, set it's style accordingly
      if (revealed) {
        blockButton.getStyleClass().clear();
        if (isMine) {
          blockButton.getStyleClass().add("mine");
        } else {
          blockButton.getStyleClass().add("safe");
        }

        revealAdjacentMines();
      }
    }

    /**
     * Get the correct image dimensions for each difficulty
     * @param level difficulty level
     * @return image width for the given diffuclty
     */
    public int getImageDimensions(Grid.Level level) {
      if (level.equals(Grid.Level.EASY)) return EASY_IMG_DIM;
      else if (level.equals(Grid.Level.MEDIUM)) return MEDIUM_IMG_DIM;
      else return HARD_IMG_DIM;
    }
    
    /**
     * Sets the block's number of adjacent mines
     * (Does not update the block UI, only modifies the internal state)
     * @param adjacentMines number of adjacent mines
     */
    public void setAdjacentMines(int adjacentMines) {
      this.adjacentMines = adjacentMines;
    }

    /**
     * Sets the block's onReveal function
     * @param onReveal function to be ran when the block is revealed
     */
    public void setOnReveal(TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal) {
      this.onReveal = onReveal;
    }

    /**
     * Reveals the block, showing whether or not it is a mine or a blank square.
     */
    public void reveal() {
      // Return if block was already revealed
      if (revealed) {
        return; 
      }
      
      // Update moves made
      Minesweeper.getGame().setMovesMade(Minesweeper.getGame().getMovesMade() + 1);

      // Update styling
      blockButton.getStyleClass().clear();
      if (isMine) {
        blockButton.getStyleClass().add("mine");
      } else {
        blockButton.getStyleClass().add("safe");
      }
      
      // Set block state to revealed
      revealed = true;

      // Update UI
      revealAdjacentMines();
      currentImage.setImage(revealedImage.getImage());
      onReveal.apply(getIsMine(), adjacentMines, getLocation());

      // Check if the game is now Won
      Minesweeper.getGame().isGameWon();
    }

    /**
     * Display a flag on the block!
     */
    public void placeFlag() {
      if (revealed) 
        return;
      currentImage.setImage(flaggedImage.getImage());
    }

    /**
     * Displays the number of adjacent mines on the block if the block is not a mine
     */
    public void revealAdjacentMines() {
      if (!isMine)
        buttonLabel.setText(adjacentMines > 0 ? adjacentMines.toString() : "");
    }

    /**
     * @return Whether or not the block is a mine
     */
    public boolean getIsMine() {
      return isMine;
    }

    /**
     * @return Whether or not the block has been revealed
     */
    public boolean isRevealed() {
      return revealed;
    }

    /**
     * @return How many mines are adjacent to this block
     */
    public int getAdjacentMines() {
      return adjacentMines;
    }

    /**
     * Set the block's column index to keep track of it's location
     * @param i Block's column index
     */
    public void setColumn(int i) {
      c = i;
    }

    /**
     * Set the block's row index to keep track of it's location
     * @param i Block's row index
     */
    public void setRow(int i) {
      r = i;
    }

    /**
     * Gets the location of the block in the Grid
     * @return Pair of the block's column and row indexes
     */
    public Pair<Integer, Integer> getLocation() {
      return new Pair<>(c, r);
    }

    @Override
    public Node render() throws Exception {
      if (adjacentMines == null)
        throw new Exception("The number of AdjacentMines must be set before rendering a block.");

      return blockButton;
    }
}
