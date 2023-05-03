package Unit5.Timer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class TimerApp extends Application{
    @Override
    public void start(Stage primaryStage) {
        VBox container = new VBox(20);

        // create the timer
        VBox timer = new TimerComponent(30).getContainer();


        // collect and display the tasks
        // VBox taskList = new Tasks().getContainer();

        container.getChildren().addAll(timer);

        Scene scn = new Scene(container, 400, 300);
        primaryStage.setTitle("Focus Timer");
        primaryStage.setScene(scn);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
