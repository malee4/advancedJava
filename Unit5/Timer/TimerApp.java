package Unit5.Timer;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TimerApp extends Application{
    private Color color = Color.WHITE;
    public static Background background = new Background(new BackgroundFill(Color.RED, null, null));
    public static VBox container = new VBox(20);
    
    @Override
    public void start(Stage primaryStage) {
        
        Insets padding = new Insets(30); // aesthetics
        container.setPadding(padding);

        // create the timer
        VBox timer = new TimerComponent(5).getContainer();
        timer.setAlignment(Pos.CENTER);

        // collect and display the tasks
        VBox taskList = new Tasks().getContainer();

        container.getChildren().addAll(timer, taskList);

        container.setBackground(background);

        Scene scn = new Scene(container, 400, 400);
        primaryStage.setTitle("Focus Timer");
        primaryStage.setScene(scn);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
