package com.blackout.util.language;

public enum LanguageEnum {
  ENGLISH("en"),
  GERMAN("de"),
  FRENCH("fr"),
  ITALIAN("it");

  private String code;

  LanguageEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}