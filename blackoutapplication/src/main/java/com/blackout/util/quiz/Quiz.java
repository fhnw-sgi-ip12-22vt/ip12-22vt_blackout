package com.blackout.util.quiz;

import com.blackout.util.config.ConfigManager;
import com.blackout.util.exception.BlackoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Quiz {
  private final int amountPerRound =
      Integer.parseInt(ConfigManager.getProperty("app.settings.questionsPerRound"));
  private List<Question> questions;

  public List<Integer> generateRandomIntegers() {
    List<Integer> randomIntegers = new ArrayList<>();
    Random random = new Random();
    int max = questions.size() - 1;
    int min = 0;
    try {
      while (randomIntegers.size() != amountPerRound) {
        int randomInteger = random.nextInt((max - min) + 1) + min;
        if (!randomIntegers.contains(randomInteger)) {
          randomIntegers.add(randomInteger);
        }
      }
    } catch (Exception e) {
      throw new BlackoutException(
          "Error while generating random integers: this happens most likely, when trying to create more questions per round than availble in questions.json. Check your system-config on startup and/or questions.json in /db.");
    }

    return randomIntegers;
  }

  public List<Integer> generateRandomIntegers(List<Question> questions) {
    List<Integer> randomIntegers = new ArrayList<>();
    Random random = new Random();
    int max = questions.size() - 1;
    int min = 0;
    try {
      while (randomIntegers.size() != amountPerRound) {
        int randomInteger = random.nextInt((max - min) + 1) + min;
        if (!randomIntegers.contains(randomInteger)) {
          randomIntegers.add(randomInteger);
        }
      }
    } catch (Exception e) {
      throw new BlackoutException(
          "Error while generating random integers: this happens most likely, when trying to create more questions per round than availble in questions.json. Check your system-config on startup and/or questions.json in /db.");
    }

    return randomIntegers;
  }

  public QuestionSet setQuestionSet() {
    List<Integer> randomIntegers = generateRandomIntegers(questions);
    if (questions.size() < amountPerRound) {
      throw new BlackoutException(
          "Error: less questions available than set in app.questions.amountPerRound");
    }

    List<Question> set = new ArrayList<>();
    for (Integer randomInteger : randomIntegers) {
      set.add(questions.get(randomInteger));
    }

    return new QuestionSet(set);
  }

  public QuestionSet setQuestionSet(String roomId) {
    List<Question> possibleQuestions = new ArrayList<>();
    for (Question question : questions) {
      if (Objects.equals(question.getRoomId(), roomId)) {
        possibleQuestions.add(question);
      }
    }

    List<Integer> randomIntegers = generateRandomIntegers(possibleQuestions);
    if (possibleQuestions.size() < amountPerRound) {
      throw new BlackoutException(
          "Error: less questions available than set in app.questions.amountPerRound");
    }

    List<Question> set = new ArrayList<>();
    for (Integer randomInteger : randomIntegers) {
      set.add(possibleQuestions.get(randomInteger));
    }

    return new QuestionSet(set);
  }

  public QuestionSet setQuestionSet(List<Integer> randomIntegers) {
    if (questions.size() < amountPerRound) {
      throw new BlackoutException(
          "Error: less questions available than set in app.questions.amountPerRound");
    }

    List<Question> set = new ArrayList<>();
    for (Integer randomInteger : randomIntegers) {
      set.add(questions.get(randomInteger));
    }

    return new QuestionSet(set);
  }
}