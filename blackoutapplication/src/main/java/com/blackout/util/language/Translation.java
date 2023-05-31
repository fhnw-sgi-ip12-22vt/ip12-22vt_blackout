package com.blackout.util.language;

import java.util.Map;

class Translation {
  private Map<String, String> texts;

  public String getText(String key) {
    return texts.get(key);
  }

  public boolean hasText(String key) {
    return texts != null && texts.containsKey(key);
  }

  public String[] getAllKeys() {
    return texts != null ? texts.keySet().toArray(new String[0]) : new String[0];
  }
}
