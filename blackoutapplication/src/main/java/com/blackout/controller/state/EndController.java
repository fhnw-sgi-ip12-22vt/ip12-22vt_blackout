package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.view.gui.state.EndView;
import com.blackout.view.gui.state.StateView;
import javafx.animation.PauseTransition;

public class EndController extends StateController {
  PauseTransition timeOut;

  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public EndController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
  }

  @Override
  public void shutdown() {
    super.shutdown();
  }

  @Override
  protected void actionAckOverride() {
    model.reset();
    stateMachine.changeState(new IdleController(stateMachine, model));
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new EndView(isPlayerA);
    view.init(this);
    return view;
  }
}
