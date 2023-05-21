package Final;

// import packages
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.Node;

/**
 * Creates containers for menu options containing the Level setter, instructions manual, and reset button. 
 */
public class Menu implements UIElement {
    private VBox vContainer = new VBox(10); // container for the vertical version of the menu
    private HBox hContainer = new HBox(10); // container for the horizontal version of the menu
    private MenuButton levelSelector;
    
    public Menu() {
        // add the title
        Label title = new Label("Welcome to Minesweeper!");
        title.setAlignment(Pos.CENTER);

        // label for Levels menu
        Label menuInstructions = new Label("Select your level of difficulty.");
        menuInstructions.setAlignment(Pos.CENTER);
        Label level = new Label("Level: ");

        // set Easy menu option
        MenuItem easy = new MenuItem("Easy");
        easy.setOnAction(e-> {
            try {
                levelSelector.setText("Easy");
                Minesweeper.getGrid().setLevel(Grid.Level.EASY);
                Minesweeper.getGame().setMovesMade(0);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // set Medium menu option
        MenuItem medium = new MenuItem("Medium");
        medium.setOnAction(e-> {
            try {
                levelSelector.setText("Medium");
                Minesweeper.getGrid().setLevel(Grid.Level.MEDIUM);
                Minesweeper.getGame().setMovesMade(0);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // set Hard menu option
        MenuItem hard = new MenuItem("Hard");
        hard.setOnAction(e-> {
            try {
                levelSelector.setText("Hard");
                Minesweeper.getGrid().setLevel(Grid.Level.HARD);
                Minesweeper.getGame().setMovesMade(0);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // load options into menu
        levelSelector = new MenuButton("Easy", null, easy, medium, hard);

        // load elements into vertical menu container
        vContainer.getChildren().addAll(title, menuInstructions, levelSelector);
        vContainer.setAlignment(Pos.CENTER);
        
        // load elements into horizontal menu container
        hContainer.getChildren().addAll(level, levelSelector);
        hContainer.setAlignment(Pos.CENTER);
    }

    /**
     * Renders the vertical menu
     * 
     * @return Node of the vertical menu container
     * 
    */
    @Override
    public Node render() {
        return vContainer;
    }

    /**
     * Gets the Level menu
     * 
     * @return MenuButton of the Level menu with Easy, Medium, and Hard options
     */
    public MenuButton getLevelSelector() {
        return levelSelector;
    }

    /**
     * Renders the horizontal menu
     * 
     * @return Node of the horizontal menu container
     */
    public Node getHorizontal() {
        return hContainer;
    }
    
}