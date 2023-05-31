package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.Game;
import com.blackout.model.StateModel;
import com.blackout.model.SyncState;
import com.blackout.view.gui.state.ResultView;
import com.blackout.view.gui.state.StateView;

public class ResultController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  protected ResultController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
  }

  @Override
  public void shutdown() {
    super.shutdown();
  }

  @Override
  protected void actionAckOverride() {
    if (model.getQuestionSet().hasNextQuestion()) {
      model.getQuestionSet().setNextQuestion();

      if (model.game.getValue().equals(Game.VS)) {
        model.syncState.setValue(SyncState.WaitsForOtherUserToConfirmResult);
        stateMachine.changeState(new SyncController(stateMachine, model));
      } else if (model.game.getValue().equals(Game.Single)) {
        stateMachine.changeState(new QuestionController(stateMachine, model));
      }

    } else {
      stateMachine.changeState(new EndController(stateMachine, model));
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new ResultView(isPlayerA);
    view.init(this);
    return view;
  }
}
