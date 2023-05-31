package com.blackout.component;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import java.util.logging.Logger;

public class LEDButton {
  private static final long DEFAULT_DEBOUNCE = 10_000;
  private final DigitalInput digitalInput;
  private final DigitalOutput digitalOutput;
  private final Logger logger = Logger.getLogger(getClass().getName());
  private Runnable onDown;

  public LEDButton(Context pi4j, PIN led, PIN btn) {
    this.digitalOutput = pi4j.create(buildDigitalOutputConfig(pi4j, led));
    this.digitalInput = pi4j.create(buildDigitalInputConfig(pi4j, btn));
    this.digitalInput.addListener(digitalStateChangeEvent -> {
      DigitalState state = switch (digitalInput.state()) {
        case HIGH -> DigitalState.HIGH;
        case LOW -> DigitalState.LOW;
        default -> DigitalState.UNKNOWN;
      };
      logDebug("Button switched to '" + state + "'");
      switch (state) {
        case HIGH -> {
          if (onDown != null) {
            onDown.run();
          }
        }
        case UNKNOWN -> logError("Button is in State UNKNOWN");
      }
    });
  }

  private static DigitalInputConfig buildDigitalInputConfig(Context pi4j, PIN address) {
    return DigitalInput.newConfigBuilder(pi4j).id("BCM" + address)
        .name("Button #" + address)
        .address(address.getPin())
        .debounce(DEFAULT_DEBOUNCE)
        .build();
  }

  private static DigitalOutputConfig buildDigitalOutputConfig(Context pi4j, PIN address) {
    return DigitalOutput.newConfigBuilder(pi4j)
        .id("BCM" + address)
        .name("LED")
        .address(address.getPin())
        .build();
  }

  public void onDown(Runnable task) {
    this.onDown = task;
  }

  public void setLed(boolean value) {
    if (value) {
      digitalOutput.on();
    } else {
      digitalOutput.off();
    }
  }

  private void logError(String msg) {
    logger.severe(() -> msg);
  }

  private void logDebug(String msg) {
    logger.fine(() -> msg);
  }
}
