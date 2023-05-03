package Unit5.ColorMixer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.geometry.*;

/**
 * Primarily for testing purposes
 */

public class Gradient extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        Stop[] stops = new Stop[] {new Stop(0, Color.CADETBLUE), new Stop(1, Color.PURPLE)};
        LinearGradient lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        Rectangle r1 = new Rectangle(0, 0, 100, 100);
        r1.setFill(lg);
        layout.getChildren().addAll(r1);

        Scene scn = new Scene(layout, 100, 100);
        primaryStage.setScene(scn);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
