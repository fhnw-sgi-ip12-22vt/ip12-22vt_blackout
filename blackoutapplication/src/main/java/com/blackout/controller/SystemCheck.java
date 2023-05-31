package com.blackout.controller;

import com.blackout.model.AppModel;
import com.blackout.util.audio.AudioConfig;
import com.blackout.util.mvcbase.ObservableValue;
import com.blackout.util.sound.SoundFx;

public class SystemCheck {
  private final AppModel model;
  private boolean isRunning;

  public SystemCheck(AppModel model) {
    this.model = model;
  }

  public void startCheck() {
    isRunning = true;
    SoundFx.soundStageA.init(AudioConfig.getAudioConfigA());
    SoundFx.soundStageB.init(AudioConfig.getAudioConfigB());
  }

  public boolean getIsRunning() {
    return isRunning;
  }

  public void stopCheck() {
    disableLed(model.ledA);
    disableLed(model.ledB);
    disableLed(model.ledC);
    disableLed(model.ledAckA);
    disableLed(model.ledZ);
    disableLed(model.ledY);
    disableLed(model.ledX);
    disableLed(model.ledAckB);
    SoundFx.soundStageA.stopAll();
    SoundFx.soundStageB.stopAll();
    SoundFx.soundStageA.switchToSpeaker();
    SoundFx.soundStageB.switchToSpeaker();
    isRunning = false;
  }

  public boolean actionA() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledA);
    return true;
  }

  public boolean actionB() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledB);
    return true;
  }

  public boolean actionC() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledC);
    return true;
  }

  public boolean actionAckA() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledAckA);
    return true;
  }

  public boolean actionZ() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledZ);
    return true;
  }

  public boolean actionY() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledY);
    return true;
  }

  public boolean actionX() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledX);
    return true;
  }

  public boolean actionAckB() {
    if (!isRunning) {
      return false;
    }
    toggleLed(model.ledAckB);
    return true;
  }

  private void toggleLed(ObservableValue<Boolean> led) {
    led.setValue(!led.getValue());
  }

  private void disableLed(ObservableValue<Boolean> led) {
    led.setValue(false);
  }
}
