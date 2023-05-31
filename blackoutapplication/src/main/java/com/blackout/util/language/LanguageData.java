package com.blackout.util.language;

import java.util.Map;

class LanguageData {
  private Map<String, Translation> translations;

  public Translation getTranslation(LanguageEnum language) {
    return translations.get(language.getCode());
  }

  public boolean hasTranslation(LanguageEnum language) {
    return translations.containsKey(language.getCode());
  }

  public String[] getAllKeys() {
    return translations.values().iterator().next().getAllKeys();
  }
}
