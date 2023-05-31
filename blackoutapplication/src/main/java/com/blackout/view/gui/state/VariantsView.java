package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.language.LanguageLoader;
import com.blackout.util.language.TextKeyEnum;
import com.blackout.util.sound.Sound;
import com.blackout.util.sound.SoundFx;
import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class VariantsView extends StateView {
  private final double SCALE = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
  private Button buttonPrimary;
  private Button buttonSecondary;
  private Button buttonTertiary;
  private Button buttonAck;
  private ImageView logoView;
  private ImageView option1View;
  private Label option1Text;
  private ImageView option1SelectedView;
  private ImageView option3View;
  private Label option3Text;
  private ImageView option3SelectedView;
  private ImageView putOnHeadphonesView;
  private Label putOnHeadphonesText;
  private MediaPlayer mediaPlayer;
  private MediaView mediaView;
  private LanguageLoader languageLoader = new LanguageLoader();

  public VariantsView(boolean isPlayerA) {
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

    String folderUrl = "/views/variants/";

    // putOnHeadphones
    Image putOnHeadphonesImage = getImage(folderUrl + "put_on_headphones.png");
    putOnHeadphonesView = new ImageView(putOnHeadphonesImage);
    putOnHeadphonesView.setFitWidth(1920 * SCALE);
    putOnHeadphonesView.setFitHeight(1080 * SCALE);
    putOnHeadphonesView.setLayoutX(0 * SCALE);
    putOnHeadphonesView.setLayoutY(0 * SCALE);

    putOnHeadphonesText = new Label();
    putOnHeadphonesText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 32 * 2));
    putOnHeadphonesText.setLayoutX(207.2 * SCALE);
    putOnHeadphonesText.setLayoutY(249 * SCALE);
    putOnHeadphonesText.setMaxWidth(1506.4 * SCALE);
    putOnHeadphonesText.setWrapText(true);

    // logo
    Image logoImage = getImage(folderUrl + "logo.png");
    logoView = new ImageView(logoImage);
    logoView.setFitWidth(57.9 * SCALE);
    logoView.setFitHeight(57.9 * SCALE);
    logoView.setLayoutX(30 * SCALE);
    logoView.setLayoutY(30 * SCALE);

    // option1
    Image option1Image = getImage(folderUrl + "option1.png");
    option1View = new ImageView(option1Image);
    option1View.setFitWidth(640 * SCALE);
    option1View.setFitHeight(172.5 * SCALE);
    option1View.setLayoutX(0 * SCALE);
    option1View.setLayoutY(907.5 * SCALE);

    option1Text = new Label();
    option1Text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 8 * 2));
    option1Text.setLayoutX(110.1 * SCALE);
    option1Text.setLayoutY(936.1 * SCALE);
    option1Text.setMaxWidth(480.3 * SCALE);
    option1Text.setWrapText(true);

    // option1Selected
    Image option1SelectedImage = getImage(folderUrl + "option1_selected.png");
    option1SelectedView = new ImageView(option1SelectedImage);
    option1SelectedView.setFitWidth(640 * SCALE);
    option1SelectedView.setFitHeight(172.5 * SCALE);
    option1SelectedView.setLayoutX(0 * SCALE);
    option1SelectedView.setLayoutY(907.5 * SCALE);

    // option3
    Image option3Image = getImage(folderUrl + "option3.png");
    option3View = new ImageView(option3Image);
    option3View.setFitWidth(640 * SCALE);
    option3View.setFitHeight(172.5 * SCALE);
    option3View.setLayoutX(1280 * SCALE);
    option3View.setLayoutY(907.5 * SCALE);

    option3Text = new Label();
    option3Text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 8 * 2));
    option3Text.setLayoutX(1407.6 * SCALE);
    option3Text.setLayoutY(936.1 * SCALE);
    option3Text.setMaxWidth(480.3 * SCALE);
    option3Text.setWrapText(true);

    // option3Selected
    Image option3SelectedImage = getImage(folderUrl + "option3_selected.png");
    option3SelectedView = new ImageView(option3SelectedImage);
    option3SelectedView.setFitWidth(640 * SCALE);
    option3SelectedView.setFitHeight(172.5 * SCALE);
    option3SelectedView.setLayoutX(1280 * SCALE);
    option3SelectedView.setLayoutY(907.5 * SCALE);

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
            Objects.requireNonNull(IdleView.class.getResource("/views/variants/variants_en.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.VARIANTS_EN));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.VARIANTS_EN));
      }
      case "de" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/variants/variants_de.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.VARIANTS_DE));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.VARIANTS_DE));
      }
      case "fr" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/variants/variants_fr.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.VARIANTS_FR));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.VARIANTS_FR));
      }
      case "it" -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/variants/variants_it.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.VARIANTS_IT));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.VARIANTS_IT));
      }
      default -> {
        Media media = new Media(
            Objects.requireNonNull(IdleView.class.getResource("/views/variants/variants_de.mp4"))
                .toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnPlaying(() -> soundStage.play(Sound.VARIANTS_DE));
        mediaPlayer.setOnStopped(() -> soundStage.stop(Sound.VARIANTS_DE));
      }
    }
    mediaView = new MediaView(mediaPlayer);
    mediaView.setFitWidth(1920 * SCALE);
    mediaView.setFitHeight(1080 * SCALE);

    PauseTransition delay = new PauseTransition(Duration.seconds(3));
    delay.setOnFinished((e) -> {
      putOnHeadphonesView.setVisible(false);
      putOnHeadphonesText.setVisible(false);
    });
    delay.play();
  }

  @Override
  public void layoutParts() {
    if (Objects.equals(ConfigManager.getProperty("app.settings.showButtons"), "true")) {
      getChildren().addAll(mediaView, logoView, option1View, option1SelectedView, option1Text,
          option3View, option3SelectedView, option3Text, putOnHeadphonesView, putOnHeadphonesText,
          buttonPrimary, buttonSecondary, buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(mediaView, logoView, option1View, option1SelectedView, option1Text,
          option3View, option3SelectedView, option3Text, putOnHeadphonesView, putOnHeadphonesText);
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
    super.setupModelToUiBindings(model);
    onChangeOf(model.option1Selected).update(option1SelectedView.visibleProperty());
    onChangeOf(model.option3Selected).update(option3SelectedView.visibleProperty());

    languageLoader.loadTextToLabel(putOnHeadphonesText, TextKeyEnum.PUT_ON_HEADPHONES, isPlayerA);
    languageLoader.loadTextToLabel(option1Text, TextKeyEnum.SINGLE, isPlayerA);
    languageLoader.loadTextToLabel(option3Text, TextKeyEnum.VERSUS, isPlayerA);
  }
}
