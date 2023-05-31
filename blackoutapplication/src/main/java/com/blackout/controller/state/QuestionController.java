package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.Game;
import com.blackout.model.StateModel;
import com.blackout.model.SyncState;
import com.blackout.view.gui.state.QuestionView;
import com.blackout.view.gui.state.StateView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class QuestionController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public QuestionController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.resetOptions();
  }

  @Override
  public void shutdown() {
    super.shutdown();
  }

  @Override
  protected void actionPrimaryOverride() {
    model.option1Selected.setValue(!model.option1Selected.getValue());
    model.getQuestionSet().getQuestion().setAnswer(0);
  }

  @Override
  protected void actionSecondaryOverride() {
    model.option2Selected.setValue(!model.option2Selected.getValue());
    model.getQuestionSet().getQuestion().setAnswer(1);
  }

  @Override
  protected void actionTertiaryOverride() {
    model.option3Selected.setValue(!model.option3Selected.getValue());
    model.getQuestionSet().getQuestion().setAnswer(2);
  }

  @Override
  protected void actionAckOverride() {
    if (Boolean.TRUE.equals(model.option1Selected.getValue())
        || Boolean.TRUE.equals(model.option2Selected.getValue())
        || Boolean.TRUE.equals(model.option3Selected.getValue())) {
      int points = 3;

      for (int s : model.getQuestionSet().getQuestion().getSolution()) {
        if (!model.getQuestionSet().getQuestion().getAnswer().contains(s)) {
          points--;
        }
      }
      for (int a : model.getQuestionSet().getQuestion().getAnswer()) {
        if (!model.getQuestionSet().getQuestion().getSolution().contains(a)) {
          points--;
        }
      }
      model.getQuestionSet().getQuestion().setPoints(points);

      if (model.game.getValue().equals(Game.Single)) {
        model.syncState.setValue(SyncState.WantsToPlayAlone);
        stateMachine.changeState(new ResultController(stateMachine, model));
      }

      if (model.game.getValue().equals(Game.VS)) {
        model.syncState.setValue(SyncState.WaitsForOtherUserToAnswerQuestion);
        stateMachine.changeState(new SyncController(stateMachine, model));
      }
    } else {
      model.alertVisible.setValue(true);
      PauseTransition delay = new PauseTransition(Duration.seconds(1));
      delay.setOnFinished(e -> model.alertVisible.setValue(false));
      delay.play();
    }
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new QuestionView(isPlayerA);
    view.init(this);
    return view;
  }
}