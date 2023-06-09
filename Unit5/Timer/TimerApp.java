package Unit5.Timer;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TimerApp extends Application{
    public static Background background = new Background(new BackgroundFill(Color.RED, null, null));
    public static VBox container = new VBox(20);
    
    @Override
    public void start(Stage primaryStage) {
        
        Insets padding = new Insets(30); // aesthetics
        container.setPadding(padding);

        // create the timer
        Node timer = new TimerComponent().render();

        // collect and display the tasks
        VBox taskList = new Tasks().getContainer();

        container.getChildren().addAll(timer, taskList);

        container.getStyleClass().add("vbox");

        //container.setBackground(background);

        Scene scn = new Scene(container, 400, 400);
        scn.getStylesheets().add("Unit5/Timer/TimerAppStyleSheet.css");
        primaryStage.setTitle("Focus Timer");
        primaryStage.setScene(scn);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
