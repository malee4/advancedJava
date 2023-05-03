package Unit5.Timer;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.Pos;

public class Tasks {
    private VBox container = new VBox(10);
    private VBox taskContainer = new VBox(5);
    private TextField input = new TextField();

    public Tasks() {
        Label instructions = new Label("Enter your tasks below.");
        instructions.setAlignment(Pos.CENTER);

        Button enter = new Button("Enter");
        enter.setAlignment(Pos.CENTER);
        enter.setOnAction(e -> {
            String taskText = input.getText();
            input.clear();
            // if (taskText.length() > 100) taskText = taskText.substring(0, 100) + "...";
            Label task = new Label(taskText);
            task.setMaxWidth(100);
            task.setWrapText(true);
            taskContainer.getChildren().add(task);
        });

        Button clear = new Button("Clear tasks");
        clear.setAlignment(Pos.CENTER);
        clear.setOnAction(e -> {
            taskContainer.getChildren().clear();
        });

        container.getChildren().addAll(taskContainer, clear, instructions, input, enter);
        container.setAlignment(Pos.CENTER);
    }

    public VBox getContainer() {
        return container;
    }
    
}
