package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.Game;
import com.blackout.model.StateModel;
import com.blackout.model.SyncState;
import com.blackout.view.gui.state.StateView;
import com.blackout.view.gui.state.SyncView;

public class SyncController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  protected SyncController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.resetOptions();
  }

  @Override
  protected void actionEventOverride() {
    if (Boolean.TRUE.equals(model.synced.getValue())
        && model.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmVS)
        && model.game.getValue().equals(Game.VS)) {
      stateMachine.changeState(new QuestionController(stateMachine, model));
    }

    if (Boolean.TRUE.equals(model.synced.getValue())
        && model.syncState.getValue().equals(SyncState.WaitsForOtherUserToAnswerQuestion)
        && model.game.getValue().equals(Game.VS)) {
      stateMachine.changeState(new ResultController(stateMachine, model));
    }

    if (Boolean.TRUE.equals(model.synced.getValue())
        && model.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmResult)
        && model.game.getValue().equals(Game.VS)) {
      stateMachine.changeState(new QuestionController(stateMachine, model));
    }
  }

  @Override
  protected void actionTertiaryOverride() {
    super.actionTertiaryOverride();
    if (Boolean.FALSE.equals(model.synced.getValue())) {
      model.option3Selected.setValue(!model.option3Selected.getValue());
    }
  }

  @Override
  protected void actionAckOverride() {
    if (Boolean.TRUE.equals(model.option3Selected.getValue())
        && Boolean.FALSE.equals(model.synced.getValue())) {
      model.game.setValue(Game.Undefined);
      model.syncState.setValue(SyncState.Undefined);
      stateMachine.changeState(new VariantsController(stateMachine, model));
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new SyncView(isPlayerA);
    view.init(this);
    return view;
  }
}
