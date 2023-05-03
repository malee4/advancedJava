package Unit5.WhackAMole;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import java.io.*;


public class WhackAMole extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int MOLE_SIZE = 60;
    private static final int GAME_DURATION_SECONDS = 30;
    private static final int SCORE_INCREMENT = 10;

    private int score;
    private int timeRemaining;
    private Text scoreText;
    private Text timeText;
    private Random random;
    private Timeline timeline;

    @Override
    public void start(Stage stage) {

        // Initialize the game variables
        score = 0;
        timeRemaining = GAME_DURATION_SECONDS;
        random = new Random();

        // Set up the game UI
        Pane pane = new Pane();
        ImageView background = new ImageView(new Image("background.jpg", WIDTH, HEIGHT, false, true));
        pane.getChildren().add(background);

        scoreText = new Text("Score: " + score);
        scoreText.setFont(new Font(20));
        scoreText.setX(20);
        scoreText.setY(30);
        pane.getChildren().add(scoreText);

        timeText = new Text("Time: " + timeRemaining);
        timeText.setFont(new Font(20));
        timeText.setX(WIDTH - 120);
        timeText.setY(30);
        pane.getChildren().add(timeText);

        // Add moles to the game
        for (int i = 0; i < 6; i++) {
            addMoleToPane(pane);
        }

        // Set up the game timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(120), event -> {
            timeRemaining--;
            timeText.setText("Time: " + timeRemaining);
            if (timeRemaining <= 0) {
                timeline.stop();
                gameOver();
            }
        }));
        timeline.setCycleCount(GAME_DURATION_SECONDS);
        timeline.play();

        // Set up the scene
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setTitle("Whack-a-Mole!");
        stage.setScene(scene);
        stage.show();
    }

    private void addMoleToPane(Pane pane) {
        try {
            FileInputStream input = new FileInputStream("mole.jpg");
            ImageView mole = new ImageView(new Image(input, MOLE_SIZE, MOLE_SIZE, false, true));
            mole.setX(random.nextInt(WIDTH - MOLE_SIZE));
            mole.setY(random.nextInt(HEIGHT - MOLE_SIZE - 50) + 50);
            mole.setOnMouseClicked(event -> {
                score += SCORE_INCREMENT;
                scoreText.setText("Score: " + score);
                pane.getChildren().remove(mole);
                addMoleToPane(pane);
            });
            pane.getChildren().add(mole);
    
            // Make the mole pop up and down randomly
            Timeline moleTimeline = new Timeline(new KeyFrame(Duration.seconds(random.nextInt(3) + 1), event -> {
                if (random.nextBoolean()) {
                    mole.setY(mole.getY() + random.nextInt(30) + 10);
                } else {
                    mole.setY(mole.getY() - random.nextInt(30) - 10);
                }
            }));
            moleTimeline.setCycleCount(Timeline.INDEFINITE);
            moleTimeline.play();
        } catch (Exception e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
        
    }

    private void gameOver() {
        scoreText.setText("Final Score: " + score);
        timeText.setText("Game over.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
