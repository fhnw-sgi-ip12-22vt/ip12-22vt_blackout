package com.blackout.controller;

import com.blackout.controller.state.ConfigController;
import com.blackout.controller.state.IdleController;
import com.blackout.controller.state.LanguageController;
import com.blackout.controller.state.StateController;
import com.blackout.controller.state.SystemController;
import com.blackout.model.AppModel;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.model.SyncState;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.mvcbase.ControllerBase;
import com.blackout.util.quiz.QuestionSet;
import com.blackout.util.sound.SoundFx;
import com.blackout.util.sound.SoundStage;
import com.blackout.view.gui.state.IdleView;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.util.Duration;

public class AppController extends ControllerBase<AppModel> {
  private final StateModel stateModelA;
  private final StateModel stateModelB;
  private final SystemCheck systemCheck;
  private StateController controllerA;
  private StateController controllerB;
  private Timer timerA;
  private Timer timerB;
  private boolean idleA;
  private boolean idleB;
  private boolean idleAPlaying;
  private boolean idleBPlaying;

  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public AppController(AppModel model) {
    super(model);
    systemCheck = new SystemCheck(model);
    stateModelA = new StateModel();
    stateModelB = new StateModel();
    stateModelA.syncState.onChange(((oldValue, newValue) -> syncVS()));
    stateModelB.syncState.onChange(((oldValue, newValue) -> syncVS()));
    controllerA = new SystemController(createStateMachineA(), stateModelA);
    model.viewA.setValue(controllerA.getView());
    controllerB = new SystemController(createStateMachineB(), stateModelB);
    model.viewB.setValue(controllerB.getView());
  }

  public void actionA() {
    if (systemCheck.actionA()) {
      return;
    }
    controllerA.actionPrimary();
  }

  public void actionB() {
    if (systemCheck.actionB()) {
      return;
    }
    controllerA.actionSecondary();
  }

  public void actionC() {
    if (systemCheck.actionC()) {
      return;
    }
    controllerA.actionTertiary();
  }

  public void actionAckA() {
    if (systemCheck.actionAckA()) {
      return;
    }
    controllerA.actionAck();
  }

  public void actionZ() {
    if (systemCheck.actionZ()) {
      return;
    }
    controllerB.actionPrimary();
  }

  public void actionY() {
    if (systemCheck.actionY()) {
      return;
    }
    controllerB.actionSecondary();
  }

  public void actionX() {
    if (systemCheck.actionX()) {
      return;
    }
    controllerB.actionTertiary();
  }

  public void actionAckB() {
    if (systemCheck.actionAckB()) {
      return;
    }
    controllerB.actionAck();
  }

  public void shutdownHard() {
    try {
      shutdown();
      Runtime.getRuntime().exec("sudo shutdown now").waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void syncVS() {
    stateModelA.synced.setValue(false);
    stateModelB.synced.setValue(false);

    var bothPlayersWaitForVSToBegin =
        stateModelA.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmVS)
            && stateModelB.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmVS);

    var bothPlayersWaitForResultScreen =
        stateModelA.syncState.getValue().equals(SyncState.WaitsForOtherUserToAnswerQuestion)
            && stateModelB.syncState.getValue().equals(SyncState.WaitsForOtherUserToAnswerQuestion);

    var bothPlayersWaitForNextQuestionOrEndScreen =
        stateModelA.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmResult)
            && stateModelB.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmResult);

    if (bothPlayersWaitForVSToBegin) {
      stateModelA.synced.setValue(true);
      stateModelB.synced.setValue(true);

      List<Integer> randomIntegers = stateModelA.getQuiz().generateRandomIntegers();

      QuestionSet questionSetA = stateModelA.getQuiz().setQuestionSet(randomIntegers);
      stateModelA.setQuestionSet(questionSetA);
      QuestionSet questionSetB = stateModelB.getQuiz().setQuestionSet(randomIntegers);
      stateModelB.setQuestionSet(questionSetB);
    }

    if (bothPlayersWaitForResultScreen || bothPlayersWaitForNextQuestionOrEndScreen) {
      stateModelA.synced.setValue(true);
      stateModelB.synced.setValue(true);
    }
  }

  public boolean isIdleTimerRequiredA() {
    boolean notInstanceOfSystem = !(controllerA instanceof SystemController);
    boolean notInstanceOfLanguageController = !(controllerA instanceof LanguageController);
    boolean notInstanceOfConfig = !(controllerA instanceof ConfigController);
    return notInstanceOfSystem && notInstanceOfLanguageController && notInstanceOfConfig;
  }

  public boolean isIdleTimerRequiredB() {
    boolean notInstanceOfSystem = !(controllerB instanceof SystemController);
    boolean notInstanceOfLanguageController = !(controllerB instanceof LanguageController);
    boolean notInstanceOfConfig = !(controllerB instanceof ConfigController);
    return notInstanceOfSystem && notInstanceOfLanguageController && notInstanceOfConfig;
  }

  public void startIdleTimerA() {
    timerA = createTimer(() -> {
      System.out.println("Change A to idle.");
      changeStateA(new IdleController(controllerA.stateMachine, stateModelA));
    }, false);
  }

  public void startIdleTimerB() {
    timerB = createTimer(() -> {
      System.out.println("Change B to idle.");
      changeStateB(new IdleController(controllerB.stateMachine, stateModelB));
    }, true);
  }

  private Timer createTimer(Action action, boolean delay) {
    long seconds = Long.parseLong(ConfigManager.getProperty("app.settings.timeoutAfter"));
    long delaymilliseconds = delay ? 500 : 0;
    long millisecondsPerSecond = 1000;
    long milliseconds = seconds * millisecondsPerSecond + delaymilliseconds;
    var timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        action.action();
      }
    }, milliseconds);
    return timer;
  }

  private void changeStateA(StateController controller) {
    model.viewA.setValue(controller.getView());
    idleA = model.viewA.getValue() instanceof IdleView;
    syncIdle();
    if ((controller instanceof IdleController)
        || (controller instanceof SystemController)
        || (controller instanceof LanguageController)) {
      SoundFx.soundStageA.switchToSpeaker();
    } else {
      SoundFx.soundStageA.switchToHeadset();
    }
    controllerA = controller;
  }

  private void changeStateB(StateController controller) {
    model.viewB.setValue(controller.getView());
    idleB = model.viewB.getValue() instanceof IdleView;
    syncIdle();
    if ((controller instanceof IdleController)
        || (controller instanceof SystemController)
        || (controller instanceof LanguageController)) {
      SoundFx.soundStageB.switchToSpeaker();
    } else {
      SoundFx.soundStageB.switchToHeadset();
    }
    controllerB = controller;
  }

  private void syncIdle() {
    if (!idleA || !idleB) {
      return;
    }

    var viewA = (IdleView ) model.viewA.getValue();
    var viewB = (IdleView ) model.viewB.getValue();
    viewA.mute = false;
    viewB.mute = true;
    viewA.mediaPlayer.stop();
    viewB.mediaPlayer.stop();
    viewA.mediaPlayer.setStartTime(Duration.ZERO);
    viewB.mediaPlayer.setStartTime(Duration.ZERO);
    viewA.mediaPlayer.play();
    viewB.mediaPlayer.play();
  }

  private StateMachine createStateMachineA() {
    return new StateMachine() {
      @Override
      public void changeState(StateController controller) {
        if (timerA != null) {
          timerA.cancel();
        }
        changeStateA(controller);
      }

      @Override
      public SoundStage getSoundStage() {
        return SoundFx.soundStageA;
      }

      @Override
      public void startSystemCheck() {
        systemCheck.startCheck();
      }

      @Override
      public boolean isSystemCheckRunning() {
        return systemCheck.getIsRunning();
      }

      @Override
      public void stopSystemCheck() {
        systemCheck.stopCheck();
      }

      @Override
      public ConsoleTyp getConsoleType() {
        return ConsoleTyp.A;
      }
    };
  }

  private StateMachine createStateMachineB() {
    return new StateMachine() {
      @Override
      public void changeState(StateController controller) {
        if (timerB != null) {
          timerB.cancel();
        }
        changeStateB(controller);
      }

      @Override
      public SoundStage getSoundStage() {
        return SoundFx.soundStageB;
      }

      @Override
      public void startSystemCheck() {
        systemCheck.startCheck();
      }

      @Override
      public boolean isSystemCheckRunning() {
        return systemCheck.getIsRunning();
      }

      @Override
      public void stopSystemCheck() {
        systemCheck.stopCheck();
      }

      @Override
      public ConsoleTyp getConsoleType() {
        return ConsoleTyp.B;
      }
    };
  }

  @FunctionalInterface
  interface Action {
    void action();
  }
}