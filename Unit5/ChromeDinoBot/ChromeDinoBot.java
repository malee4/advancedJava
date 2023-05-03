package Unit5.ChromeDinoBot;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.robot.Robot;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/*
* Assume constant speed
* coordinates: 0 at top, 0 at left
* Ground occurs when y = 656, dino feet at 677
* Max tree height at y = 531
* Game over bounded by y = 481, 573
        x = 908, 1012
* Dino is at 208, 612
* Dino height is |540 - 677| = 137
* Image dimensions: 1920 x 1080
*/

public class ChromeDinoBot extends Application {
    
    public static final Color GRAY = Color.rgb(83, 83, 83);
    public static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    public static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    public void jump() {
        Robot robot = new Robot();
        robot.keyType(KeyCode.UP);
    }
    
    // public void duck() {
    //     Robot robot = new Robot();
    //     // robot.keyType(KeyCode.DOWN);
    //     robot.keyPress(KeyCode.DOWN);
        
    // }

    // Checks to see if a catci is present in front of the dino.
    // (If this is the case, it might be a good idea to jump to not die T.T)
    public boolean cactiPresentSoon() {
        Robot robot = new Robot();
        // Color color = robot.getPixelColor(136 + 40, 1080 - 612 - 10);
        // Color color = robot.getPixelColor(350, 1080 - 629 - 20);
        // Color color = robot.getPixelColor(358, 1080 - 622);
        return robot.getPixelColor(350, 1080 - 600).equals(GRAY) || 
               robot.getPixelColor(350, 1080 - 550).equals(GRAY) ||
               robot.getPixelColor(350, 1080 - 650).equals(GRAY); 
        // return color.equals(GRAY);
    }
    
    // checks to see if the game is over
    public boolean isGameOver() {
        return false;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // delay for x number seconds?
        Thread.sleep( 3 * 1000);

        while (true) {
        //   System.out.println(cactiPresentSoon());
          if (cactiPresentSoon()) {
            jump();
          }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
  
}