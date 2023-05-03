package Unit5.Timer;

import java.util.TimerTask;
import java.util.function.Consumer;

import javafx.application.Platform;

class PomodoroTimerTask extends TimerTask {
  private int secondsLength;
  private int secondsElapsed = 0;
  private Consumer<Integer> secondsCallback;
  private Runnable completedCallback;
  
  public PomodoroTimerTask(int secondsLength, Consumer<Integer> secondsCallback, Runnable completedCallback) {
    this.secondsLength = secondsLength;
    this.secondsCallback = secondsCallback;
    this.completedCallback = completedCallback;
  }

  public PomodoroTimerTask() {
    this.secondsLength = 1500;
    this.secondsCallback = timeElapsed -> System.out.println("Seconds callback placeholder"); 
    this.completedCallback = () -> System.out.println("Completed callback placeholder"); 
  }

  @Override
  public void run() {
    Platform.runLater(() -> {
      secondsElapsed += 1;
      secondsCallback.accept(secondsElapsed);
      
      if (secondsElapsed == secondsLength) {
        completedCallback.run();
        cancel();
    }
    });
  }
}
