package com.blackout.util.exception;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class TimeOutHandler {
  public static PauseTransition getHandler(Runnable action) {
    PauseTransition timeOut = new PauseTransition(Duration.seconds(600));
    timeOut.setOnFinished((e) -> action.run());
    timeOut.play();
    return timeOut;
  }
}
