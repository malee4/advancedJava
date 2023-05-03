package Unit5.Magic8;

import java.nio.file.*;
import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Magic8 extends Application {
    public static String[] messages = new String[20];

    @Override
    public void start(Stage primaryStage) {
        

        // add the text box
        Label instructions = new Label();
        instructions.setText("Put your text here: ");
        TextField userField = new TextField();
        
        HBox hBox = new HBox(instructions, userField);
        hBox.setAlignment(Pos.CENTER);

        Label msg = new Label();

         // create the button
         Button btn = new Button("Ask the Magic-8 Ball a Question.");
         btn.setOnAction(e -> {
             userField.clear();
             msg.setText(messages[(int)(Math.random()*20)]);
         });
 
         VBox vBox = new VBox(20);
         vBox.getChildren().addAll(hBox, btn, msg); // vertical box
         vBox.setAlignment(Pos.CENTER);

        Scene scn = new Scene(vBox, 300, 250);

        primaryStage.setTitle("Magic-8 Ball");
        primaryStage.setScene(scn);
        primaryStage.show();

        return;
    }

    public static void main(String[] args) {

        String path = "/Users/melod/Desktop/academia/2223/java/Unit5/Magic8/responses.txt";

        try {
            if (Files.exists(Paths.get(path))) {
                String text = Files.readString(Paths.get(path));
                messages = text.split("\n");
            }
        } catch (IOException e) {
            System.out.println("Error in reading in responses.txt.");
        }

        launch(args);

    }
    
}
