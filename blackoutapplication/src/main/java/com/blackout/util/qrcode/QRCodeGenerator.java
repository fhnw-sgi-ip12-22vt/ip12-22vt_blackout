package com.blackout.util.qrcode;

import com.blackout.util.config.ConfigManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class QRCodeGenerator {
  private static final String URL = ConfigManager.getProperty("app.server.report.url");
  private static final int WIDTH = 350;
  private static final int HEIGHT = 350;

  private QRCodeGenerator() {
  }

  /**
   * @param allQuestionsInfo
   * @return
   */
  public static Image returnQrImage(String allQuestionsInfo, boolean isPlayerA) {
    String date = LocalDateTime.now().toString();
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    String language;

    if (isPlayerA) {
      language = ConfigManager.getProperty("app.settings.language.A");
    } else {
      language = ConfigManager.getProperty("app.settings.language.B");
    }

    try {
      BitMatrix bitMatrix =
          qrCodeWriter.encode(
              URL + "?questionsInfo=" +
                  allQuestionsInfo +
                  "&language=" +
                  language +
                  "&date=" +
                  date,
              BarcodeFormat.QR_CODE,
              WIDTH,
              HEIGHT,
              hints);

      // Todo: remove
      System.out.println(URL + "?questionsInfo=" +
          allQuestionsInfo +
          "&language=" +
          language +
          "&date=" +
          date);

      int width = bitMatrix.getWidth();
      int height = bitMatrix.getHeight();
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics = image.createGraphics();
      graphics.setColor(java.awt.Color.WHITE);
      graphics.fillRect(0, 0, width, height);
      graphics.setColor(java.awt.Color.BLACK);

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          if (bitMatrix.get(x, y)) {
            graphics.fillRect(x, y, 1, 1);
          }
        }
      }

      return SwingFXUtils.toFXImage(image, null);
    } catch (WriterException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
