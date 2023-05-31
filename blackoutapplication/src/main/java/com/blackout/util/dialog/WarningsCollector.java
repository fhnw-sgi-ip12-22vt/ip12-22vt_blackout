package com.blackout.util.dialog;

public class WarningsCollector {
  static StringBuilder sb = new StringBuilder();

  private WarningsCollector() {
  }

  public static void add(String warning) {
    sb.append(warning).append("\n");
  }

  public static String get() {
    return sb.toString();
  }
}
