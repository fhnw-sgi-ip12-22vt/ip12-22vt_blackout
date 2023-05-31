package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.quiz.QuestionSet;
import com.blackout.util.sound.Sound;
import com.blackout.util.sound.SoundFx;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class EndView extends StateView {
  private final double SCALE = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
  private Button buttonPrimary;
  private Button buttonSecondary;
  private Button buttonTertiary;
  private Button buttonAck;
  private ImageView logoView;
  private ImageView qrPlaceholderView;
  private ImageView qrCodeView;
  private MediaPlayer mediaPlayer;
  private MediaView mediaView;

  public EndView(boolean isPlayerA) {
    super(isPlayerA);
  }

  private Image getImage(String imageUrl) {
    return ImageManager.getInstance().getImage(imageUrl);
  }

  @Override
  public void initializeParts() {
    buttonPrimary = new Button("Button A");
    buttonPrimary.setLayoutX(10);
    buttonPrimary.setLayoutY(10);
    buttonSecondary = new Button("Button B");
    buttonSecondary.setLayoutY(10);
    buttonSecondary.setLayoutX(buttonPrimary.getLayoutX() + 100);
    buttonTertiary = new Button("Button C");
    buttonTertiary.setLayoutY(10);
    buttonTertiary.setLayoutX(buttonSecondary.getLayoutX() + 100);
    buttonAck = new Button("Button OK");
    buttonAck.setLayoutY(10);
    buttonAck.setLayoutX(buttonTertiary.getLayoutX() + 100);

    String folderUrl = "/views/end/";

    // logo
    Image logoImage = getImage(folderUrl + "logo.png");
    logoView = new ImageView(logoImage);
    logoView.setFitWidth(57.9 * SCALE);
    logoView.setFitHeight(57.9 * SCALE);
    logoView.setLayoutX(30 * SCALE);
    logoView.setLayoutY(30 * SCALE);

    // qrPlaceholder
    Image qrPlaceholderImage = getImage(folderUrl + "qr_placeholder.png");
    qrPlaceholderView = new ImageView(qrPlaceholderImage);
    qrPlaceholderView.setFitWidth(597.7 * SCALE);
    qrPlaceholderView.setFitHeight(597.7 * SCALE);
    qrPlaceholderView.setLayoutX(1255 * SCALE);
    qrPlaceholderView.setLayoutY(200.8 * SCALE);

    // qrCode
    qrCodeView = new ImageView();
    qrCodeView.setFitWidth(547.2 * SCALE);
    qrCodeView.setFitHeight(547.2 * SCALE);
    qrCodeView.setLayoutX(1281 * SCALE);
    qrCodeView.setLayoutY(226 * SCALE);

    // Todo: remove ugly workaround
    String language;

    if (isPlayerA) {
      language = ConfigManager.getProperty("app.settings.language.A");
    } else {
      language = ConfigManager.getProperty("app.settings.language.B");
    }

    switch (language) {
      case "en" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/end/end_en.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.END_EN));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.END_EN));
      }
      case "de" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/end/end_de.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.END_DE));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.END_DE));
      }
      case "fr" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/end/end_fr.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.END_FR));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.END_FR));
      }
      case "it" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/end/end_it.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.END_IT));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.END_IT));
      }
      default -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/end/end_de.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.END_EN));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.END_EN));
      }
    }
    mediaView = new MediaView(mediaPlayer);
    mediaView.setFitWidth(1920 * SCALE);
    mediaView.setFitHeight(1080 * SCALE);
  }

  @Override
  public void layoutParts() {
    if (Objects.equals(ConfigManager.getProperty("app.settings.showButtons"), "true")) {
      getChildren().addAll(mediaView, logoView, qrPlaceholderView, qrCodeView, buttonPrimary,
          buttonSecondary, buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(mediaView, logoView, qrPlaceholderView, qrCodeView);
    }
  }

  @Override
  public void setupUiToActionBindings(StateController controller) {
    buttonPrimary.setOnAction(e -> controller.actionPrimary());
    buttonSecondary.setOnAction(e -> controller.actionSecondary());
    buttonTertiary.setOnAction(e -> controller.actionTertiary());
    buttonAck.setOnAction(e -> {
      mediaPlayer.stop();
      controller.actionAck();
    });
  }

  @Override
  public void setupModelToUiBindings(StateModel model) {
    QuestionSet questionSet;
    super.setupModelToUiBindings(model);
    questionSet = model.getQuestionSet();
    Image qrCodeImage = questionSet.getQrCode(isPlayerA);
    qrCodeView.setImage(qrCodeImage);
  }
}
