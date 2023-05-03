package Unit5.Timer;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Timer;

public class TimerComponent {
    private PomodoroTimerTask timerTask;
    private Timer timer = new Timer();

    private int length = 25 * 60;
    private boolean paused = true;

    public enum TimerState {
      WORK,
      SHORT_BREAK,
      LONG_BREAK;

      @Override
      public String toString() {
        switch (this) {
          case WORK:
            return "Work";
          case SHORT_BREAK:
            return "Short Break";
          case LONG_BREAK:
            return "Long Break";
          default:
            return "";
        }
      }
    }

    private VBox container = new VBox(20);

    private Label timerTitleLabel = new Label(TimerState.WORK.toString());
    private Label timeElapsedLabel = new Label(secondsToTimeString(length));

    private Button startPauseButton = new Button("Start");

    public TimerComponent(int length) {
        this.length = length;
        
        startPauseButton.setOnAction(e -> {
          if (paused) {
            startPauseButton.setText("Pause");
            paused = false;

            // timerTask = new PomodoroTimerTask(length, timeElapsed -> timeElapsedLabel.setText(secondsToTimeString(length - timeElapsed)), () -> {});
            timerTask = new PomodoroTimerTask(length, timeElapsed -> System.out.println("hii " + timeElapsed), () -> {});

          } else {
            startPauseButton.setText("Start");
            paused = true;

            timerTask.cancel();
          }
        });

        container.getChildren().addAll(timerTitleLabel, timeElapsedLabel, startPauseButton);
    }

    public int getLength() {
        return length;
    }

    private String secondsToTimeString(int seconds) {
      int minutes = seconds / 60;
      int secondsRemainder = seconds % 60;

      return minutes + ":" + secondsRemainder;
    }

    public VBox getContainer() {
      return container;
    }
}
