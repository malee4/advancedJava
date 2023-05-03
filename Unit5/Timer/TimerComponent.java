package Unit5.Timer;

import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Timer;

public class TimerComponent {
    private PomodoroTimerTask timerTask;
    private Timer timer = new Timer();

    private int length = 25 * 60;
    private boolean paused = true;
    private TimerMode mode = TimerMode.WORK;

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
            return 25;
          case BREAK:
            return 5;
          default:
            return 5;
        }
      }
    }

    private VBox container = new VBox(10);

    private Button switchStateButton = new Button("Break");

    private Label timerTitleLabel = new Label(mode.toString());
    private Label timeElapsedLabel = new Label(secondsToTimeString(length));

    private Button startPauseButton = new Button("Start");

    public TimerComponent() {
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
          
          if (timerTask != null)
            timerTask.cancel();
        });
        
        startPauseButton.setOnAction(e -> {
          if (paused) {
            startPauseButton.setText("Pause");
            paused = false;
            
            // System.out.println(this.length);
            timerTask = new PomodoroTimerTask(this.length, timeElapsed -> timeElapsedLabel.setText(secondsToTimeString(this.length - timeElapsed)), () -> {
              paused = true;
              startPauseButton.setText("Start");
            });
            // timerTask = new PomodoroTimerTask(length, timeElapsed -> System.out.println("hii " + timeElapsed), () -> {});
            timer.schedule(timerTask, 1000, 1000);

          } else {
            startPauseButton.setText("Start");
            paused = true;

            timerTask.cancel();
          }
        });

        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(timerTitleLabel, switchStateButton, timeElapsedLabel, startPauseButton);
    }

    public int getLength() {
        return length;
    }

    private String secondsToTimeString(int seconds) {
      int minutes = seconds / 60;
      int secondsRemainder = seconds % 60;

      return String.format("%02d", minutes) + ":" + String.format("%02d", secondsRemainder);
    }

    public Node render() {
      return container;
    }
}
