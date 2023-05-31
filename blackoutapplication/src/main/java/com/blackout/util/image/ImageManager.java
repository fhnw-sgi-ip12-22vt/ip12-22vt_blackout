package com.blackout.util.image;

import com.blackout.util.config.ConfigManager;
import com.blackout.util.exception.BlackoutException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class ImageManager {
  private static ImageManager instance;
  private Map<String, Image> imageCache = new HashMap<>();

  private ImageManager() {
  }

  public static ImageManager getInstance() {
    if (instance == null) {
      instance = new ImageManager();
    }
    return instance;
  }

  public Image getImage(String imageUrl) {
    Image image = imageCache.get(imageUrl);

    if (image == null) {
      InputStream is = ImageManager.class.getResourceAsStream(imageUrl);
      if (is == null) {
        throw new BlackoutException(ConfigManager.getProperty("Error: resource not found"));
      }
      image = new Image(is);
      imageCache.put(imageUrl, image);
    }

    return image;
  }
}
