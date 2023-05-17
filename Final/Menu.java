package Final;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.Node;

public class Menu implements UIElement {
    private VBox vContainer = new VBox(10);
    private HBox hContainer = new HBox(10);
    private MenuButton levelSelector;
    
    public Menu() {
        // add the title
        Label title = new Label("Welcome to Minesweeper!");
        title.setAlignment(Pos.CENTER);
        Label menuInstructions = new Label("Select your level of difficulty.");
        menuInstructions.setAlignment(Pos.CENTER);
        // create the UI
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

        levelSelector = new MenuButton("Easy", null, easy, medium, hard);

        vContainer.getChildren().addAll(title, menuInstructions, levelSelector);
        vContainer.setAlignment(Pos.CENTER);

        Label level = new Label("Level: ");
        hContainer.getChildren().addAll(level, levelSelector);
        hContainer.setAlignment(Pos.CENTER);
    }

    @Override
    public Node render() {
        return vContainer;
    }

    public MenuButton getLevelSelector() {
        return levelSelector;
    }

    public Node getHorizontal() {
        return hContainer;
    }
    
}
