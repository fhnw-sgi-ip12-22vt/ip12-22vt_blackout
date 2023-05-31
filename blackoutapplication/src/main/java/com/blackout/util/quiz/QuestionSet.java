package com.blackout.util.quiz;

import com.blackout.util.exception.BlackoutException;
import com.blackout.util.qrcode.QRCodeGenerator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public class QuestionSet {
  public final SimpleBooleanProperty isAnswer1Selected = new SimpleBooleanProperty(false);
  public final SimpleBooleanProperty isAnswer2Selected = new SimpleBooleanProperty(false);
  public final SimpleBooleanProperty isAnswer3Selected = new SimpleBooleanProperty(false);
  public final SimpleBooleanProperty isAnswer4Selected = new SimpleBooleanProperty(false);
  public final SimpleIntegerProperty questionNumber = new SimpleIntegerProperty(1);
  private final List<Question> questions;
  private Question question;

  public QuestionSet(List<Question> questions) {
    this.questions = questions;
    setCurrentQuestion();
  }

  public void setCurrentQuestion() {
    try {
      question = questions.get(questionNumber.get() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new BlackoutException("Error: tried to access non-existent question");
    }
  }

  public int getAmountOfQuestions() {
    return questions.size();
  }

  public Question getQuestion() {
    return question;
  }

  public void setNextQuestion() {
    questionNumber.set(questionNumber.get() + 1);
    setCurrentQuestion();
  }

  public boolean hasNextQuestion() {
    try {
      questions.get(questionNumber.get());
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  public int getTotalPoints() {
    int points = 0;
    for (Question question : questions) {
      points += question.getPoints();
    }
    return points;
  }

  public String getAllQuestionsInfo() {
    StringBuilder sb = new StringBuilder();
    for (Question question : questions) {
      int questionId = question.getQuestionId();
      List<Integer> userAnswers = question.getAnswer();
      String userAnswersString =
          userAnswers.stream().map(String::valueOf).collect(Collectors.joining(","));
      int points = question.getPoints();
      sb.append(questionId)
          .append("-")
          .append(userAnswersString)
          .append("-")
          .append(points)
          .append(";");
    }
    return sb.toString();
  }

  public Image getQrCode(boolean isPlayerA) {
    String allQuestionsInfo = getAllQuestionsInfo();
    return QRCodeGenerator.returnQrImage(allQuestionsInfo, isPlayerA);
  }
}