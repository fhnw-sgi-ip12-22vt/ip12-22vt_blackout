package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.view.gui.state.StateView;
import com.blackout.view.gui.state.SystemView;

public class SystemController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public SystemController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.reset();
  }

  @Override
  protected void actionPrimaryOverride() {
    super.actionPrimaryOverride();
    model.option1Selected.setValue(!model.option1Selected.getValue());
    model.option3Selected.setValue(false);
  }

  @Override
  protected void actionTertiaryOverride() {
    super.actionTertiaryOverride();
    model.option3Selected.setValue(!model.option3Selected.getValue());
    model.option1Selected.setValue(false);
  }

  @Override
  protected void actionAckOverride() {
    super.actionAckOverride();
    if (Boolean.TRUE.equals(model.option1Selected.getValue())) {
      stateMachine.changeState(new ConfigController(stateMachine, model));
    }

    if (Boolean.TRUE.equals(model.option3Selected.getValue())) {
      stateMachine.changeState(new IdleController(stateMachine, model));
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new SystemView(isPlayerA);
    view.init(this);
    return view;
  }
}
