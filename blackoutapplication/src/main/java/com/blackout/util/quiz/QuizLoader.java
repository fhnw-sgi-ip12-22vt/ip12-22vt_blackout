package com.blackout.util.quiz;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class QuizLoader {
  public static Quiz loadQuiz(String filePath) throws IOException {
    Gson gson = new Gson();
    return gson.fromJson(new FileReader(filePath), Quiz.class);
  }
}