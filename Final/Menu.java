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
            Minesweeper.getGrid().setLevel(Grid.Level.EASY);
        });

        MenuItem medium = new MenuItem("Medium");
        medium.setOnAction(e-> {
            Minesweeper.getGrid().setLevel(Grid.Level.MEDIUM);
        });
        MenuItem hard = new MenuItem("Hard");
        hard.setOnAction(e-> {
            Minesweeper.getGrid().setLevel(Grid.Level.HARD);
        });

        levelSelector = new MenuButton("Levels", null, easy, medium, hard);
        // levelSelector.setAlignment(Pos.CENTER);

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

    public Node getHorizontal() {
        return hContainer;
    }
    
}
