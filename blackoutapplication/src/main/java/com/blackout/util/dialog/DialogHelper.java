package com.blackout.util.dialog;

import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

public class DialogHelper {
  /**
   * @param title
   * @param message
   */
  public static void showMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(message);
  }

  /**
   * @param title
   * @param message
   * @param durationInSeconds
   */
  public static void showMessage(String title, String message, int durationInSeconds) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(message);

    PauseTransition delay = new PauseTransition(Duration.seconds(durationInSeconds));
    delay.setOnFinished(e -> alert.close());
    delay.play();

    alert.showAndWait();
  }

  /**
   * @param title
   * @param message
   * @return
   */
  public static boolean confirmMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(message);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }
}
