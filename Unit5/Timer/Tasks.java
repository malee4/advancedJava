package Unit5.Timer;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.Pos;

public class Tasks {
    private VBox container = new VBox(10);
    private VBox taskContainer = new VBox(5);
    private TextField input = new TextField();
    private Label tasksRemainingText = new Label();
    private int taskCounter = 0;

    public Tasks() {
        Label instructions = new Label("Enter your tasks below.");
        instructions.setAlignment(Pos.CENTER);

        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(120, 120);
        s1.setContent(taskContainer);
        s1.getStyleClass().add("tasks");

        Button enter = new Button("Enter");
        enter.setAlignment(Pos.CENTER);
        enter.setOnAction(e -> {
            
            String inputText = input.getText();
            if (inputText.length() != 0) {
                CheckBox check = new CheckBox(inputText);
                check.setOnAction(c -> {
                    if (check.isSelected())
                      taskCounter--;
                    else
                      taskCounter++;
                
                    tasksRemainingText.setText("Tasks remaining: " + taskCounter);
                });

                // update the task counter
                taskCounter++;
                tasksRemainingText.setText("Tasks remaining: " + taskCounter);

                // Label taskText = new Label(inputText);
                check.setMaxWidth(390);
                check.setWrapText(true);
                
                taskContainer.getChildren().add(check);
            }
            input.clear();
        });

        Button clear = new Button("Clear tasks");
        clear.setAlignment(Pos.CENTER);
        clear.setOnAction(e -> {
            taskContainer.getChildren().clear();
        });

        container.getChildren().addAll(s1, tasksRemainingText, clear, instructions, input, enter);
        container.setAlignment(Pos.CENTER);
    }

    public VBox getContainer() {
        return container;
    }
    
}
