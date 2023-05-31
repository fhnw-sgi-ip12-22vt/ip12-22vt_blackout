package com.blackout.util.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
  private static final String CONFIG_FILE = "db/config.properties";
  private static final Properties properties;

  static {
    properties = new Properties();
    try {
      InputStream inputStream = new FileInputStream(CONFIG_FILE);
      properties.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private ConfigManager() {
  }

  public static void setProperty(String key, String value) {
    properties.setProperty(key, value);
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }
}
