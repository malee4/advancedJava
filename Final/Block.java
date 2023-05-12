package Final;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.util.function.Consumer;

public class Block implements UIElement {
    private final Button blockButton;
    // private final int WIDTH = 10;
    private final boolean isMine;

    // Called when the block is clicked on and revealed.
    // Callback is called with true if the block is a mine, false if it is not.
    private final Consumer<Boolean> onReveal;
    private boolean revealed = false;

    private ImageView hiddenImage; // shown before the user clicks on the block
    private ImageView revealedImage; // shown after the user clicks on the block
    private ImageView currentImage;
    private final Label buttonLabel;

    private Integer adjacentMines;

    // dimensions for image
    private static final int EASY_IMG_DIM = 15;
    private static final int MEDIUM_IMG_DIM = 10;
    private static final int HARD_IMG_DIM = 5;

    public Block(boolean isMine, Consumer<Boolean> onReveal, Grid.Level level) {
      this.isMine = isMine;
      this.onReveal = onReveal;
      
      blockButton = new Button("");
      blockButton.getStyleClass().add("block");

      try {
        hiddenImage = new ImageView(new Image(new FileInputStream("./Final/assets/blank.png")));

        if (isMine)
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/mine.png")));
        else
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/safe.png")));
        
      } catch (IOException e) {
        System.out.println("Error loading block image assets: " + e);
        System.exit(0);
      }

      currentImage = new ImageView(hiddenImage.getImage());

      // adjust the dimensions of the image
      int imgDim = getImageDimensions(level);
      currentImage.setFitHeight(imgDim);
      currentImage.setFitWidth(imgDim);

      buttonLabel = new Label("0");
      StackPane buttonPane = new StackPane(currentImage, buttonLabel);

      blockButton.setGraphic(buttonPane);
      blockButton.setOnAction(e -> reveal());
    }

    public int getImageDimensions(Grid.Level level) {
      if (level.equals(Grid.Level.EASY)) return EASY_IMG_DIM;
      else if (level.equals(Grid.Level.MEDIUM)) return MEDIUM_IMG_DIM;
      else return HARD_IMG_DIM;
    }
    
    public void setAdjacentMines(int adjacentMines) {
      this.adjacentMines = adjacentMines;
      buttonLabel.setText(this.adjacentMines.toString());
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
      currentImage.setImage(revealedImage.getImage());
      onReveal.accept(isMine);
    }

    public boolean getIsMine() {
      return isMine;
    }

    public Node render() throws Exception {
      if (adjacentMines == null)
        throw new Exception("The number of Adjacent mines must be set before rendering a block.");

      return blockButton;
    }
}