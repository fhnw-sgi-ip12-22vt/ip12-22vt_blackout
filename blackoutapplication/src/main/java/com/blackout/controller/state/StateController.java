package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.util.mvcbase.ControllerBase;
import com.blackout.util.sound.Sound;
import com.blackout.view.gui.state.StateView;

public abstract class StateController extends ControllerBase<StateModel> {
  public final StateMachine stateMachine;

  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  protected StateController(StateMachine stateMachine, StateModel model) {
    super(model);
    this.stateMachine = stateMachine;
  }

  public ConsoleTyp getConsoleType() {
    return stateMachine.getConsoleType();
  }

  public final void actionPrimary() {
    stateMachine.getSoundStage().play(Sound.BUTTON);
    actionPrimaryOverride();
    actionAnyButtonOverride();
  }

  public final void actionSecondary() {
    stateMachine.getSoundStage().play(Sound.BUTTON);
    actionSecondaryOverride();
    actionAnyButtonOverride();
  }

  public final void actionTertiary() {
    stateMachine.getSoundStage().play(Sound.BUTTON);
    actionTertiaryOverride();
    actionAnyButtonOverride();
  }

  public final void actionAck() {
    stateMachine.getSoundStage().play(Sound.BUTTON);
    actionAckOverride();
    actionAnyButtonOverride();
  }

  public final void actionEvent() {
    actionEventOverride();
  }

  protected void actionPrimaryOverride() {
  }

  protected void actionSecondaryOverride() {
  }

  protected void actionTertiaryOverride() {
  }

  protected void actionAckOverride() {
  }

  protected void actionAnyButtonOverride() {
  }

  protected void actionEventOverride() {
  }

  public abstract StateView getView();
}
