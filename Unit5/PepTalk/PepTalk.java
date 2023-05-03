package Unit5.PepTalk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class PepTalk extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static String[] readFile(String filePath) {
        String[] out = new String[16];
        System.out.println(filePath + " exists: " + Files.exists(Paths.get(filePath)));
        try {
            if (Files.exists(Paths.get(filePath))) {
                String text = Files.readString(Paths.get(filePath));
                out = text.split("\n");
                return out;
            }
        } catch (IOException e) {
            System.out.println("Error in reading in responses.txt.");
        }
        return out;
    }

    public static void writeFile(String filePath, String text) {
        try {
            String[] existingText = readFile(filePath);
            String saveText = "";
            for (int i = 0; i < existingText.length; i++) {
                saveText += existingText[i] + "\n";
            }
            saveText += text;
            Files.writeString(Paths.get(filePath), saveText);
        } catch (IOException e) {
            System.out.println("Error in writing to file.");
        }
        return;
    }

    public void start(Stage primaryStage) {
        String[] option1 = readFile("Unit5/PepTalk/Option1.txt");
        String[] option2 = readFile("Unit5/PepTalk/Option2.txt");
        String[] option3 = readFile("Unit5/PepTalk/Option3.txt");

        // output
        Label pepTalk = new Label();

        // option 1
        // create each ComboBox
        ComboBox<String> chooseOption1 = new ComboBox<String>();
        chooseOption1.getItems().addAll(option1);

        ComboBox<String> chooseOption2 = new ComboBox<String>();
        chooseOption2.getItems().addAll(option2);

        ComboBox<String> chooseOption3 = new ComboBox<String>();
        chooseOption3.getItems().addAll(option3);
        
        // arrange them
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(chooseOption1, chooseOption2, chooseOption3);
        hBox.setAlignment(Pos.CENTER);

        Button dropDownTalk = new Button("Use this pep talk!");
        dropDownTalk.setAlignment(Pos.CENTER);
        dropDownTalk.setOnAction(action -> {
            if (chooseOption1.getValue() != null && chooseOption2.getValue() != null && chooseOption3.getValue() != null) {
                String msg = chooseOption1.getValue() + " " + chooseOption2.getValue() + " " + chooseOption3.getValue();
                pepTalk.setText(msg);
            } else {
                pepTalk.setText("Please select one element for each of the options.");
            }
            
        });

        VBox opt1VBox = new VBox(10);
        opt1VBox.getChildren().addAll(hBox, dropDownTalk);
        opt1VBox.setAlignment(Pos.CENTER);

        // option 2
        Button btn = new Button();
        btn.setText("Generate my pep talk!");
        btn.setOnAction(e -> {
            String msg = "";
            String[] suggestions = new String[0];

            if (Files.exists(Paths.get("Unit5/PepTalk/myTalks.txt"))) {
                // update the current list of suggestions
                suggestions = readFile("Unit5/PepTalk/myTalks.txt");
            }
            // determine the choice 
            int choice = (int) (Math.random() * (option1.length + suggestions.length));
                
            if (suggestions.length != 0 && choice < suggestions.length) {
                msg = suggestions[choice];
            } else {
                msg += option1[(int) (Math.random() * option1.length)] + " ";
                //System.out.println((int) (Math.random() * option1.length));
                msg += option2[(int) (Math.random() * option2.length)] + " ";
                msg += option3[(int) (Math.random() * option3.length)];
            }
            
            pepTalk.setText(msg);
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(btn, pepTalk);
        vBox.setAlignment(Pos.CENTER);

        // add your own affirmations
        Label addTalkInstructions = new Label("Write your own affirmations!");
        addTalkInstructions.setAlignment(Pos.CENTER);

        TextArea textArea = new TextArea();

        Button addTalkButton = new Button("Add my pep talk!");
        addTalkButton.setOnAction(action -> {
            String talk = textArea.getText();
            if (!talk.equals("Pep talk recorded.") && talk.length() != 0) {
                writeFile("Unit5/PepTalk/myTalks.txt", talk);
                textArea.setText("Pep talk recorded.");
            }
        });

        VBox addTalkBox = new VBox(10);
        addTalkBox.getChildren().addAll(addTalkInstructions, textArea, addTalkButton);
        addTalkBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30);
        layout.getChildren().addAll(opt1VBox, vBox, addTalkBox);
        layout.setAlignment(Pos.CENTER);

        Scene scn = new Scene(layout, 600, 500);;

        primaryStage.setTitle("Pep Talk Generator");
        primaryStage.setScene(scn);
        primaryStage.show();

        return;
    }
}
