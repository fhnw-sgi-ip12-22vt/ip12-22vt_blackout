package com.blackout.model;

import com.blackout.util.exception.BlackoutException;
import com.blackout.util.mvcbase.ObservableValue;
import com.blackout.util.quiz.QuestionSet;
import com.blackout.util.quiz.Quiz;
import com.blackout.util.quiz.QuizLoader;
import java.io.IOException;

public class StateModel {
  public final ObservableValue<Boolean> synced = new ObservableValue<>(false);
  public final ObservableValue<Game> game = new ObservableValue<>(Game.Undefined);
  public final ObservableValue<SyncState> syncState = new ObservableValue<>(SyncState.Undefined);
  public final ObservableValue<Boolean> option1Selected = new ObservableValue<>(false);
  public final ObservableValue<Boolean> option2Selected = new ObservableValue<>(false);
  public final ObservableValue<Boolean> option3Selected = new ObservableValue<>(false);
  public final ObservableValue<Boolean> alertVisible = new ObservableValue<>(false);
  public ObservableValue<String> audioOutputs = new ObservableValue<>("");
  public ObservableValue<String> configText = new ObservableValue<>("");
  public ObservableValue<String> language = new ObservableValue<>("Deutsch");
  private Quiz quiz;
  private QuestionSet questionSet;

  public StateModel() {
    setQuiz();
  }

  public void reset() {
    synced.setValue(false);
    game.setValue(Game.Undefined);
    syncState.setValue(SyncState.Undefined);

    option1Selected.setValue(false);
    option2Selected.setValue(false);
    option3Selected.setValue(false);
    alertVisible.setValue(false);
  }

  public void resetOptions() {
    option1Selected.setValue(false);
    option2Selected.setValue(false);
    option3Selected.setValue(false);
    alertVisible.setValue(false);
  }

  public void setQuiz() {
    try {
      this.quiz = QuizLoader.loadQuiz("db/questions.json");
    } catch (IOException e) {
      throw new BlackoutException("Error: questions.json not found");
    }
  }

  public Quiz getQuiz() {
    return quiz;
  }

  public void setQuestionSet() {
    this.questionSet = quiz.setQuestionSet();
  }

  public void setQuestionSet(String roomId) {
    this.questionSet = quiz.setQuestionSet(roomId);
  }

  public QuestionSet getQuestionSet() {
    return questionSet;
  }

  public void setQuestionSet(QuestionSet questionSet) {
    this.questionSet = questionSet;
  }
}