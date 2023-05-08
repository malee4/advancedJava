package Final;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Menu {
    private VBox container = new VBox(10);
    private MenuButton levelSelector;
    
    public Menu() {
        // add the title
        Label title = new Label("Welcome to Minesweeper!");
        Label menuInstructions = new Label("Select your level of difficulty.");
        // create the UI
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");
        levelSelector = new MenuButton("Levels", null, easy, medium, hard);
    }
    
}
