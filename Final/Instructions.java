package Final;

import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Instructions {
    private static final String INSTRUCTIONS = "These are the instructions";
    private VBox container = new VBox(10);
    
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Instructions");
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label instructions = new Label(INSTRUCTIONS);

        Button closeInstructions = new Button("Close");

        
    }
}
