package Final;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;

import java.io.*;

import javafx.scene.input.MouseButton;

public class Block implements UIElement {
    private final Button blockButton;
    // private final int WIDTH = 10;
    private final boolean isMine;

    // Called when the block is clicked on and revealed.
    // Callback is called with true if the block is a mine, false if it is not.
    private TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal;
    private boolean revealed = false;

    private ImageView hiddenImage; // shown before the user clicks on the block
    private ImageView revealedImage; // shown after the user clicks on the block
    private ImageView currentImage;
    private ImageView flaggedImage;
    private final Label buttonLabel;

    private Integer adjacentMines;

    // so the block "knows where it is"
    private int c;
    private int r;

    // dimensions for image
    private static final int EASY_IMG_DIM = 15;
    private static final int MEDIUM_IMG_DIM = 10;
    private static final int HARD_IMG_DIM = 5;

    // Button dimension
    private static final int BUTTON_DIM = 30;

    private static final String SAFE_SOUND_FILE_PATH = "Final/assets/safeSound.wav";
    private static final String MINE_SOUND_FILE_PATH = "Final/assets/mineSound.wav";

    MediaPlayer player;

    public Block(boolean isMine, TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal, Grid.Level level) {
      this.isMine = isMine;
      this.onReveal = onReveal;
      
      blockButton = new Button("");
      blockButton.getStyleClass().add("block");

      
      try {
        hiddenImage = new ImageView(new Image(new FileInputStream("./Final/assets/blank.png")));
        flaggedImage = new ImageView(new Image(new FileInputStream("./Final/assets/flag.png")));
        
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

      currentImage = new ImageView(hiddenImage.getImage());

      // adjust the dimensions of the image
      int imgDim = getImageDimensions(level);
      currentImage.setFitHeight(imgDim);
      currentImage.setFitWidth(imgDim);

      buttonLabel = new Label("");
      StackPane buttonPane = new StackPane(currentImage, buttonLabel);

      blockButton.setGraphic(buttonPane);
      blockButton.setMinWidth(BUTTON_DIM);
      blockButton.setMaxWidth(BUTTON_DIM);
      blockButton.setMinHeight(BUTTON_DIM);
      blockButton.setMaxHeight(BUTTON_DIM);
      
      blockButton.setOnMouseClicked(e -> {
        if (e.getButton() == MouseButton.PRIMARY) {
          player.play();
          reveal();
        }

        if (e.getButton() == MouseButton.SECONDARY){
          placeFlag();
        }
      });
    }

    public Block(boolean isMine, boolean isRevealed, int adjacentMines, TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal, Grid.Level level) {
      this.isMine = isMine;
      this.onReveal = onReveal;
      this.revealed = isRevealed;
      this.adjacentMines = adjacentMines;
      
      blockButton = new Button("");
      blockButton.getStyleClass().add("block");

      try {
        hiddenImage = new ImageView(new Image(new FileInputStream("./Final/assets/blank.png")));
        flaggedImage = new ImageView(new Image(new FileInputStream("./Final/assets/flag.png")));
        
        if (isMine)
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/mine.png")));
        else
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/safe.png")));
        
      } catch (IOException e) {
        System.out.println("Error loading block image assets: " + e);
        System.exit(0);
      }

      currentImage = new ImageView(revealed ? revealedImage.getImage() : hiddenImage.getImage());

      // adjust the dimensions of the image
      int imgDim = getImageDimensions(level);
      currentImage.setFitHeight(imgDim);
      currentImage.setFitWidth(imgDim);

      buttonLabel = new Label("");
      StackPane buttonPane = new StackPane(currentImage, buttonLabel);

      blockButton.setGraphic(buttonPane);
      blockButton.setMinWidth(BUTTON_DIM);
      blockButton.setMaxWidth(BUTTON_DIM);
      blockButton.setMinHeight(BUTTON_DIM);
      blockButton.setMaxHeight(BUTTON_DIM);
      
      blockButton.setOnMouseClicked(e -> {
        if (e.getButton() == MouseButton.PRIMARY) {
          reveal();
        }

        if (e.getButton() == MouseButton.SECONDARY){
          placeFlag();
        }
      });

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

    public int getImageDimensions(Grid.Level level) {
      if (level.equals(Grid.Level.EASY)) return EASY_IMG_DIM;
      else if (level.equals(Grid.Level.MEDIUM)) return MEDIUM_IMG_DIM;
      else return HARD_IMG_DIM;
    }
    
    public void setAdjacentMines(int adjacentMines) {
      this.adjacentMines = adjacentMines;
    }

    public void setOnReveal(TriFunction<Boolean, Integer, Pair<Integer, Integer>, Void> onReveal) {
      this.onReveal = onReveal;
    }

    // Reveals the block, showing whether or not it is a mine or an empty square.
    // It returns a boolean that is true if the block is a mine and false if the block is empty.
    public void reveal() {
      // Return if block was already revealed
      if (revealed)
        return;

      blockButton.getStyleClass().clear();
      if (isMine) {
        blockButton.getStyleClass().add("mine");
      } else {
        blockButton.getStyleClass().add("safe");
      }
      
      revealed = true;

      revealAdjacentMines();
      currentImage.setImage(revealedImage.getImage());
      onReveal.apply(getIsMine(), adjacentMines, getLocation());

      Minesweeper.getGame().isGameWon();

    }

    public void placeFlag() {
      if (revealed) 
        return;
      currentImage.setImage(flaggedImage.getImage());
    }

    public void revealAdjacentMines() {
      if (!isMine)
        buttonLabel.setText(adjacentMines > 0 ? adjacentMines.toString() : "");
    }

    public boolean getIsMine() {
      return isMine;
    }

    public boolean isRevealed() {
      return revealed;
    }

    public int getAdjacentMines() {
      return adjacentMines;
    }

    public void setColumn(int i) {
      c = i;
    }

    public void setRow(int i) {
      r = i;
    }

    public Pair<Integer, Integer> getLocation() {
      return new Pair<>(c, r);
    }

    public Node render() throws Exception {
      if (adjacentMines == null)
        throw new Exception("The number of Adjacent mines must be set before rendering a block.");

      return blockButton;
    }
}