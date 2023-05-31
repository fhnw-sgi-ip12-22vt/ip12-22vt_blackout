package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.Game;
import com.blackout.model.StateModel;
import com.blackout.view.gui.state.RoomView;
import com.blackout.view.gui.state.StateView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class RoomController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public RoomController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.resetOptions();
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
    if (model.game.getValue().equals(Game.Single)) {
      stateMachine.changeState(new QuestionController(stateMachine, model));
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new RoomView(isPlayerA);
    view.init(this);
    return view;
  }
}