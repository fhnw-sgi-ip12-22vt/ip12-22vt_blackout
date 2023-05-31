package com.blackout.util.update;

import com.blackout.util.exception.BlackoutException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ConfigPropertiesChecker {
  private static final Path LOCAL_DB_PATH = Path.of("db");
  private static final Path LOCAL_CONFIG_PATH = Path.of("db/config.properties");
  private static final Path LOCAL_DEFAULT_PATH = Path.of("db/default.properties");
  private static final Path BACKUP_DEFAULT_PATH = Path.of("/backup-db/default.properties");

  private ConfigPropertiesChecker() {
  }

  public static void check() {
    if (!Files.exists(LOCAL_DB_PATH)) {
      try {
        Files.createDirectories(LOCAL_DB_PATH);
      } catch (IOException e) {
        throw new BlackoutException("Error while checking config files");
      }

      if (!Files.exists(LOCAL_CONFIG_PATH)) {
        createConfigFile(LOCAL_CONFIG_PATH);
      }

      if (!Files.exists(LOCAL_DEFAULT_PATH)) {
        createConfigFile(LOCAL_DEFAULT_PATH);
      }
    }
  }

  private static void createConfigFile(Path target) {
    try {
      InputStream inputStream =
          ConfigPropertiesChecker.class.getResourceAsStream(BACKUP_DEFAULT_PATH.toString());
      assert inputStream != null;
      Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new BlackoutException("Error while creating config file");
    }
  }
}
