package com.blackout.util.update;

import com.blackout.util.config.ConfigManager;
import com.blackout.util.dialog.WarningsCollector;
import com.blackout.util.exception.BlackoutException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

public class LanguageJsonUpdater {
  private static final Path LOCAL_LANGUAGE_PATH = Path.of("db/language.json");
  private static final String SERVER_LANGUAGE_URL =
      ConfigManager.getProperty("app.server.language.url");

  private LanguageJsonUpdater() {
  }

  private static void check() {
    if (!Files.exists(LOCAL_LANGUAGE_PATH)) {
      byte[] emptyBytes = new byte[] {};
      try {
        Files.write(LOCAL_LANGUAGE_PATH, emptyBytes);
      } catch (IOException e) {
        throw new BlackoutException("Error while creating empty language.json");
      }
    }
  }

  public static void update() {
    check();
    if (!isConnectedToInternet()) {
      WarningsCollector.add("Warning: No internet connection");
    }

    try {
      String serverFileContent = downloadFile();
      String serverFileHash = createHashFromString(serverFileContent);

      String localFileContent =
          Files.readString(LOCAL_LANGUAGE_PATH, StandardCharsets.UTF_8);
      String localFileHash = createHashFromString(localFileContent);

      if (!localFileHash.equals(serverFileHash)) {
        overwriteLocalFile(serverFileContent);
      }
    } catch (Exception e) {
      e.printStackTrace();
      WarningsCollector.add("Warning: Updating language.json failed");
    }
  }

  private static boolean isConnectedToInternet() {
    try {
      URL url = new URL("https://www.google.com");
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setConnectTimeout(3000);
      urlConnection.connect();

      return urlConnection.getResponseCode() == 200;
    } catch (Exception e) {
      return false;
    }
  }

  private static String downloadFile() throws Exception {
    URL url = new URL(SERVER_LANGUAGE_URL);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setConnectTimeout(5000);
    urlConnection.setReadTimeout(5000);
    urlConnection.connect();

    int responseCode = urlConnection.getResponseCode();
    if (responseCode != 200) {
      WarningsCollector.add("Warning: Server Error " + responseCode);
    }

    InputStream inputStream = urlConnection.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder stringBuilder = new StringBuilder();

    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
    }

    inputStream.close();
    bufferedReader.close();

    return stringBuilder.toString();
  }

  private static String createHashFromString(String input) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] messageDigest = md.digest(input.getBytes());
    BigInteger no = new BigInteger(1, messageDigest);
    String hashText = no.toString(16);

    while (hashText.length() < 32) {
      StringBuilder sb = new StringBuilder(hashText);
      sb.insert(0, "0");
      hashText = sb.toString();
    }

    return hashText;
  }

  private static void overwriteLocalFile(String content) throws Exception {
    Files.writeString(LOCAL_LANGUAGE_PATH, content, StandardCharsets.UTF_8);
  }
}
