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

public class QuestionsJsonUpdater {
  private static final Path LOCAL_QUESTIONS_PATH = Path.of("db/questions.json");
  private static final String SERVER_QUESTIONS_URL =
      ConfigManager.getProperty("app.server.questions.url");

  private QuestionsJsonUpdater() {
  }

  private static void check() {
    if (!Files.exists(LOCAL_QUESTIONS_PATH)) {
      byte[] emptyBytes = new byte[] {};
      try {
        Files.write(LOCAL_QUESTIONS_PATH, emptyBytes);
      } catch (IOException e) {
        throw new BlackoutException("Error while creating empty questions.json");
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
          Files.readString(LOCAL_QUESTIONS_PATH, StandardCharsets.UTF_8);
      String localFileHash = createHashFromString(localFileContent);

      if (!localFileHash.equals(serverFileHash)) {
        overwriteLocalFile(serverFileContent);
      }
    } catch (Exception e) {
      e.printStackTrace();
      WarningsCollector.add("Warning: Updating questions.json failed");
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
    URL url = new URL(SERVER_QUESTIONS_URL);
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
    Files.writeString(LOCAL_QUESTIONS_PATH, content, StandardCharsets.UTF_8);
  }
}
