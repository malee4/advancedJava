package Final;

import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.io.*;

public class Block implements UIElement {
    private final Button blockButton;
    // private final int WIDTH = 10;
    private final boolean isMine;

    private ImageView hiddenImage; // shown before the user clicks on the block
    private ImageView revealedImage; // shown after the user clicks on the block

    private Integer adjacentMines;

    public Block(boolean isMine) {
      this.isMine = isMine;
      blockButton = new Button("");

      try {
        hiddenImage = new ImageView(new Image(new FileInputStream("./Final/assets/hiddenBlock.png")));

        if (isMine)
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/revealedMineBlock.png")));
        else
          revealedImage = new ImageView(new Image(new FileInputStream("./Final/assets/revealedEmptyBlock.png")));
      } catch (IOException e) {
        System.out.println("Error loading block image assets: " + e);
        System.exit(0);
      }

      blockButton.set
    }
    
    public void setAdjacentMines(int adjacentMines) {
      this.adjacentMines = adjacentMines;
    }

    // Reveals the block, showing whether or not it is a mine or an empty square.
    // It returns a boolean that is true if the block is a mine and false if the block is empty.
    public boolean reveal() {
      if (isMine) {
        

        return true;
      } else {


        return false;
      }
    }

    public Node render() throws Exception {
      if (adjacentMines == null)
        throw new Exception("The number of Adjacent mines must be set before rendering a block.");

      return blockButton;
    }
}