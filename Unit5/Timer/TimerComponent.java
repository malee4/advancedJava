package Unit5.Timer;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Timer;

public class TimerComponent {
    public enum TimerMode {
      WORK,
      BREAK;

      @Override
      public String toString() {
        switch (this) {
          case WORK:
            return "Work";
          case BREAK:
            return "Break";
          default:
            return "";
        }
      }

      public int getLength() {
        switch (this) {
          case WORK:
            return 25 * 60;
          case BREAK:
            return 5 * 60;
          default:
            return 0;
        }
      }
    }
    
    private PomodoroTimerTask timerTask;
    private Timer timer = new Timer();

    private int length = 25 * 60;
    private boolean paused = true;
    private TimerMode mode = TimerMode.WORK;

    private String secondsToTimeString(int seconds) {
      int minutes = seconds / 60;
      int secondsRemainder = seconds % 60;

      return String.format("%02d", minutes) + ":" + String.format("%02d", secondsRemainder);
    }

    private VBox container = new VBox(10);

    private Button switchStateButton = new Button("Break");

    private Label timerTitleLabel = new Label(mode.toString());
    
    private Label timeElapsedLabel = new Label(secondsToTimeString(length));

    private Button startPauseButton = new Button("Start");

    public TimerComponent() {
        timerTitleLabel.getStyleClass().add("title");
        this.length = mode.getLength();
        timeElapsedLabel.setText(secondsToTimeString(length));

        switchStateButton.setOnAction(e -> {
          if (mode.equals(TimerMode.WORK)) {
            this.mode = TimerMode.BREAK;
            switchStateButton.setText("Work");
          } else if (mode.equals(TimerMode.BREAK)) {
            this.mode = TimerMode.WORK;
            switchStateButton.setText("Break");
          }
          
          this.length = this.mode.getLength();
          timeElapsedLabel.setText(secondsToTimeString(this.mode.getLength()));

          timerTitleLabel.setText(this.mode.toString());
          startPauseButton.setText("Start");
          paused = true;

          TimerApp.background = new Background(new BackgroundFill(Color.GAINSBORO, null, null));
          TimerApp.container.setBackground(TimerApp.background);
          
          if (timerTask != null)
            timerTask.cancel();
        });
        
        startPauseButton.setOnAction(e -> {
          if (paused) {
            startPauseButton.setText("Reset");
            paused = false;
            
            timerTask = new PomodoroTimerTask(this.length, timeElapsed -> timeElapsedLabel.setText(secondsToTimeString(this.length - timeElapsed)), () -> {});
            
            TimerApp.background = new Background(new BackgroundFill(Color.LIGHTGREEN, null, null));
            TimerApp.container.setBackground(TimerApp.background);
            
            timerTask = new PomodoroTimerTask(length, timeElapsed -> timeElapsedLabel.setText(secondsToTimeString(length - timeElapsed)), () -> {
              paused = true;
              TimerApp.background = new Background(new BackgroundFill(Color.PINK, null, null));
              TimerApp.container.setBackground(TimerApp.background);
              startPauseButton.setText("Start");
            });
            // timerTask = new PomodoroTimerTask(length, timeElapsed -> System.out.println("hii " + timeElapsed), () -> {});
            timer.schedule(timerTask, 1000, 1000);

          } else {
            startPauseButton.setText("Start");
           
            paused = true;

            timerTask.cancel();
            timeElapsedLabel.setText(secondsToTimeString(length));
            
          }
        });

        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(timerTitleLabel, switchStateButton, timeElapsedLabel, startPauseButton);
    }

    public int getLength() {
        return length;
    }

    public Node render() {
      return container;
    }
}