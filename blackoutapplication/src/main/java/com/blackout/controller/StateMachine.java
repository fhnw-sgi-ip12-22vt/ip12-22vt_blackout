package com.blackout.controller;

import com.blackout.controller.state.StateController;
import com.blackout.model.ConsoleTyp;
import com.blackout.util.sound.SoundStage;

public interface StateMachine {
  void changeState(StateController controller);

  SoundStage getSoundStage();

  void startSystemCheck();

  boolean isSystemCheckRunning();

  void stopSystemCheck();

  ConsoleTyp getConsoleType();
}

