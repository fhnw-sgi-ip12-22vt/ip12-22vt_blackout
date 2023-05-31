package com.blackout.util.exception;

import com.blackout.util.config.ConfigManager;
import javafx.scene.control.Alert;

public class BlackoutException extends RuntimeException {
  /**
   * @param description
   */
  public BlackoutException(String description) {
    super(description);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(ConfigManager.getProperty("app.settings.title"));
    alert.setHeaderText(null);
    alert.setContentText(description);
    alert.showAndWait();
  }

  /**
   * @param description
   * @param cause
   */
  public BlackoutException(String description, Throwable cause) {
    super(description, cause);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(ConfigManager.getProperty("app.settings.title"));
    alert.setHeaderText(null);
    alert.setContentText(description);
    alert.showAndWait();
  }
}
