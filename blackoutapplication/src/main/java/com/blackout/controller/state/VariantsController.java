package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.Game;
import com.blackout.model.StateModel;
import com.blackout.model.SyncState;
import com.blackout.view.gui.state.StateView;
import com.blackout.view.gui.state.VariantsView;

public class VariantsController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param stateMachine
   * @param model        Model managed by this Controller
   */
  protected VariantsController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.resetOptions();
  }

  @Override
  public void shutdown() {
    super.shutdown();
  }

  @Override
  protected void actionPrimaryOverride() {
    super.actionPrimaryOverride();
    model.option1Selected.setValue(!model.option1Selected.getValue());
    model.option3Selected.setValue(false);
    model.game.setValue(Game.Single);
  }

  @Override
  protected void actionTertiaryOverride() {
    super.actionTertiaryOverride();
    model.option1Selected.setValue(false);
    model.option3Selected.setValue(!model.option3Selected.getValue());
    model.game.setValue(Game.VS);
  }

  @Override
  protected void actionAckOverride() {
    super.actionAckOverride();
    stateMachine.getSoundStage().stopAll();
    if (model.game.getValue().equals(Game.Single)) {
      model.syncState.setValue(SyncState.WantsToPlayAlone);
      stateMachine.changeState(new ModeController(stateMachine, model));
    }

    if (model.game.getValue().equals(Game.VS)) {
      model.syncState.setValue(SyncState.WaitsForOtherUserToConfirmVS);
      stateMachine.changeState(new SyncController(stateMachine, model));
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new VariantsView(isPlayerA);
    view.init(this);
    return view;
  }
}
