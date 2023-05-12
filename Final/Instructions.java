package Final;

import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Pos;

public class Instructions {
    private static final String INSTRUCTIONS = "instructions go here.";
    private static VBox container = new VBox(10);
    
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Instructions");
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label instructions = new Label(INSTRUCTIONS);
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
