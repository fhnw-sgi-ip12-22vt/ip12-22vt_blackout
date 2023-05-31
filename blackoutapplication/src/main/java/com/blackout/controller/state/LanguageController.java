package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.view.gui.state.LanguageView;
import com.blackout.view.gui.state.StateView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class LanguageController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public LanguageController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.reset();
  }

  protected void actionPrimaryOverride() {
    super.actionPrimaryOverride();
    model.option1Selected.setValue(true);
    model.option3Selected.setValue(false);
    PauseTransition delay = new PauseTransition(Duration.seconds(0.25));
    delay.setOnFinished((e) -> {
      model.option1Selected.setValue(false);
    });
    delay.play();
  }

  @Override
  protected void actionTertiaryOverride() {
    super.actionTertiaryOverride();
    model.option3Selected.setValue(true);
    model.option1Selected.setValue(false);
    PauseTransition delay = new PauseTransition();
    delay.setOnFinished((e) -> {
      model.option3Selected.setValue(false);
    });
    delay.play();
  }

  @Override
  protected void actionAckOverride() {
    super.actionAckOverride();
    stateMachine.changeState(new IdleController(stateMachine, model));
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new LanguageView(isPlayerA);
    view.init(this);
    return view;
  }
}