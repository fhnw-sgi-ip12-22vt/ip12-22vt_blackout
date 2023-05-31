package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.view.gui.state.IdleView;
import com.blackout.view.gui.state.StateView;

public class IdleController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public IdleController(StateMachine stateMachine, StateModel model) {
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
    stateMachine.getSoundStage().stopAll();
    if (Boolean.TRUE.equals(model.option1Selected.getValue())) {
      stateMachine.changeState(new VariantsController(stateMachine, model));
    }
    if (Boolean.TRUE.equals(model.option3Selected.getValue())) {
      stateMachine.changeState(new LanguageController(stateMachine, model));
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new IdleView(isPlayerA);
    view.init(this);
    return view;
  }
}
