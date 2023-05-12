package Final;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

public class GameOverWindow {


    public static void display(String message) {
        Stage window = new Stage();
        window.setTitle("Game Over");
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label gameOverMsg = new Label(message + " with " + Minesweeper.getGame().getMovesMade() + " blocks revealed!");

        Button closeWindow = new Button("Try again");
        closeWindow.setOnAction(e-> {
            try {
                Minesweeper.getGame().reset();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            window.close();
            
        });

        VBox container = new VBox(10);
        container.getChildren().addAll(gameOverMsg, closeWindow);
        container.setAlignment(Pos.CENTER);

        Scene scene = new Scene(container);
        window.setScene(scene);
        window.showAndWait();
    }
    
}
