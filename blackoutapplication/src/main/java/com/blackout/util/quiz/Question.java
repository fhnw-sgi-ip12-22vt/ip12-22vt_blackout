package com.blackout.util.quiz;

import com.blackout.util.config.ConfigManager;
import java.util.ArrayList;
import java.util.List;

public class Question {
  private String roomId;
  private int questionId;
  private String questionStringEN;
  private String questionStringDE;
  private String questionStringFR;
  private String questionStringIT;
  private List<String> answerStringsEN;
  private List<String> answerStringsDE;
  private List<String> answerStringsFR;
  private List<String> answerStringsIT;
  private String explanationStringEN;
  private String explanationStringDE;
  private String explanationStringFR;
  private String explanationStringIT;
  private List<Integer> solutionArray;
  private final List<Integer> answer = new ArrayList<>();
  private int points;

  public String getRoomId() {
    return roomId;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public String getQuestionString(boolean isPlayerA) {
    if (isPlayerA) {
      switch (ConfigManager.getProperty("app.settings.language.A")) {
        case "en" -> {
          return questionStringEN;
        }
        case "de" -> {
          return questionStringDE;
        }
        case "fr" -> {
          return questionStringFR;
        }
        case "it" -> {
          return questionStringIT;
        }
        default -> {
          return questionStringDE;
        }
      }
    } else {
      switch (ConfigManager.getProperty("app.settings.language.B")) {
        case "en" -> {
          return questionStringEN;
        }
        case "de" -> {
          return questionStringDE;
        }
        case "fr" -> {
          return questionStringFR;
        }
        case "it" -> {
          return questionStringIT;
        }
        default -> {
          return questionStringDE;
        }
      }
    }

  }

  public List<String> getAnswerStrings(boolean isPlayerA) {
    if (isPlayerA) {
      switch (ConfigManager.getProperty("app.settings.language.A")) {
        case "en" -> {
          return answerStringsEN;
        }
        case "de" -> {
          return answerStringsDE;
        }
        case "fr" -> {
          return answerStringsFR;
        }
        case "it" -> {
          return answerStringsIT;
        }
        default -> {
          return answerStringsDE;
        }
      }
    } else {
      switch (ConfigManager.getProperty("app.settings.language.B")) {
        case "en" -> {
          return answerStringsEN;
        }
        case "de" -> {
          return answerStringsDE;
        }
        case "fr" -> {
          return answerStringsFR;
        }
        case "it" -> {
          return answerStringsIT;
        }
        default -> {
          return answerStringsDE;
        }
      }
    }
  }

  public List<Integer> getAnswer() {
    return answer;
  }

  public void setAnswer(int answer) {
    if (!this.answer.contains(answer)) {
      this.answer.add(answer);
    } else {
      this.answer.remove((Integer) answer);
    }
  }

  public List<Integer> getSolution() {
    return solutionArray;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public String getExplanation(boolean isPlayerA) {
    if (isPlayerA) {
      switch (ConfigManager.getProperty("app.settings.language.A")) {
        case "en" -> {
          return explanationStringEN;
        }
        case "de" -> {
          return explanationStringDE;
        }
        case "fr" -> {
          return explanationStringFR;
        }
        case "it" -> {
          return explanationStringIT;
        }
        default -> {
          return explanationStringDE;
        }
      }
    } else {
      switch (ConfigManager.getProperty("app.settings.language.B")) {
        case "en" -> {
          return explanationStringEN;
        }
        case "de" -> {
          return explanationStringDE;
        }
        case "fr" -> {
          return explanationStringFR;
        }
        case "it" -> {
          return explanationStringIT;
        }
        default -> {
          return explanationStringDE;
        }
      }
    }
  }
}