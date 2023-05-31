package com.blackout.util.language;

import com.blackout.util.config.ConfigManager;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Label;

public class LanguageLoader {
  private LanguageData languageData;
  private Set<String> keys;

  public LanguageLoader() {
    loadLanguageData();
    prepareKeys();
  }

  public void loadLanguageData() {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream("db/language.json");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    Gson gson = new Gson();
    languageData = gson.fromJson(reader, LanguageData.class);
  }

  public void prepareKeys() {
    keys = new HashSet<>(Arrays.asList(TextKeyEnum.getAllKeys()));
  }

  public String getText(TextKeyEnum key, boolean isPlayerA) {
    LanguageEnum language;

    if (isPlayerA) {
      switch (ConfigManager.getProperty("app.settings.language.A")) {
        case "en" -> language = LanguageEnum.ENGLISH;
        case "de" -> language = LanguageEnum.GERMAN;
        case "fr" -> language = LanguageEnum.FRENCH;
        case "it" -> language = LanguageEnum.ITALIAN;
        default -> language = LanguageEnum.GERMAN;
      }
    } else {
      switch (ConfigManager.getProperty("app.settings.language.B")) {
        case "en" -> language = LanguageEnum.ENGLISH;
        case "de" -> language = LanguageEnum.GERMAN;
        case "fr" -> language = LanguageEnum.FRENCH;
        case "it" -> language = LanguageEnum.ITALIAN;
        default -> language = LanguageEnum.GERMAN;
      }
    }

    Translation translation = languageData.getTranslation(language);
    return translation.getText(key.getKey());
  }

  public boolean hasText(TextKeyEnum key) {
    return keys.contains(key.getKey());
  }

  public void loadTextToLabel(Label label, TextKeyEnum key, boolean isPlayerA) {
    String text = getText(key, isPlayerA);
    label.setText(text);
  }
}
