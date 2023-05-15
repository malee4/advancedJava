package Final;

import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Instructions {
    private static final String INSTRUCTIONS = """
    (1) The objective of the game is to uncover all of the locations that do not contain mines. 

    (2) Selecting a mine will automatically end the game. 
    
    (3) Start the game by selecting any random location on the board. 
    
    (4) The numbers on each location indicate the number of mines it is in contact with, including in the directions up, down, left, right, and diagonally. 
    
    (5) If you are certain a location is not a mine, click on the location. To place a flag to mark plausible locations with mines, right click on the location.
    """;
    private static VBox container = new VBox(10);
    
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Instructions");
        window.setMinWidth(250);
        window.setMinHeight(400);

        Label instructions = new Label(INSTRUCTIONS);
        instructions.setWrapText(true);
        instructions.setMaxWidth(300);
        Insets pad = new Insets(10);
        instructions.setPadding(pad);
        Button closeInstructions = new Button("Close");
        closeInstructions.setOnAction(e-> {
            window.close();
        });

        container.getChildren().addAll(instructions, closeInstructions);
        container.setAlignment(Pos.CENTER);

        Scene scene = new Scene(container);
        window.setScene(scene);
        window.showAndWait();
    }
}
