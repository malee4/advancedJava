package Final;

// import the packages
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

/**
 * Sets and returns the characteristics of the window to be displayed when the game ends. 
 */
public class GameOverWindow {
    /**
     * Opens up a new screen (window) when called
     * 
     * @param message to be displayed in the window, indicating whether the game has been won or lost
     */
    public static void display(String message) {
        // set the characteristics of the Game Over window
        Stage window = new Stage();
        window.setMinWidth(250);
        window.setMinHeight(150);
        window.setTitle("Game Over");

        // create a label with the message
        Label gameOverMsg = new Label(message + " with " + Minesweeper.getGame().getMovesMade() + " blocks revealed!");

        // create a button to close the window and reset the game
        Button closeWindow = new Button("New Game");
        closeWindow.setOnAction(e-> {
            try {
                Minesweeper.getGame().reset(); // set button to reset the game
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            window.close();
            
        });

        // organize elements into container
        VBox container = new VBox(10);
        container.getChildren().addAll(gameOverMsg, closeWindow);
        container.setAlignment(Pos.CENTER);

        // display the window
        Scene scene = new Scene(container);
        window.setScene(scene);
        window.showAndWait(); // do not close window until prompted to do so by user
    }
    
}
