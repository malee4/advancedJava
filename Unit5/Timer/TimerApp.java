package Unit5.Timer;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class TimerApp extends Application{
  
    @Override
    public void start(Stage primaryStage) {
        VBox container = new VBox(20);
        Insets padding = new Insets(30); // aesthetics
        container.setPadding(padding);

        // create the timer
        Node timer = new TimerComponent().render();

        // collect and display the tasks
        VBox taskList = new Tasks().getContainer();

        container.getChildren().addAll(timer, taskList);

        Scene scn = new Scene(container, 400, 400);
        primaryStage.setTitle("Focus Timer");
        primaryStage.setScene(scn);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
