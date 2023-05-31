package com.blackout.util.language;

import java.util.stream.Stream;

public enum TextKeyEnum {
  START_BLACKOUT("start_blackout"),
  CHOOSE_LANGUAGE("choose_language"),
  UP("up"),
  DOWN("down"),
  PUT_ON_HEADPHONES("put_on_headphones"),
  SINGLE("single"),
  RANDOM_OR_FEEDBACK("random_or_feedback"),
  RANDOM_MODE("random_mode"),
  FEEDBACK_MODE("feedback_mode"),
  VERSUS("versus"),
  WAIT_UNTIL("wait_until"),
  QUESTION("question"),
  OF("of"),
  AT_LEAST("at_least"),
  SCORE_3("score_3"),
  SCORE_2("score_2"),
  SCORE_1("score_1"),
  UNKNOWN("unknown");

  private String key;

  TextKeyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public static String[] getAllKeys() {
    return Stream.of(TextKeyEnum.values())
        .map(TextKeyEnum::getKey)
        .toArray(String[]::new);
  }
}

