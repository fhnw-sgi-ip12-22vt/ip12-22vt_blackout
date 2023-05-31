package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.model.SyncState;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.language.LanguageLoader;
import com.blackout.util.language.TextKeyEnum;
import com.blackout.util.sound.SoundFx;
import com.blackout.util.sound.Sound;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class SyncView extends StateView {
  private final double SCALE = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
  private Button buttonPrimary;
  private Button buttonSecondary;
  private Button buttonTertiary;
  private Button buttonAck;
  private ImageView gradientView;
  private ImageView logoView;
  private Label waitUntilText;
  private ImageView threeView;
  private ImageView twoView;
  private ImageView oneView;
  private ImageView option3View;
  private Label option3Text;
  private ImageView option3SelectedView;
  private Action switchState;
  private LanguageLoader languageLoader = new LanguageLoader();

  public SyncView(boolean isPlayerA) {
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

    String folderUrl = "/views/sync/";

    // gradient
    Image gradientImage = getImage(folderUrl + "gradient.png");
    gradientView = new ImageView(gradientImage);
    gradientView.setFitWidth(1920 * SCALE);
    gradientView.setFitHeight(1080 * SCALE);
    gradientView.setLayoutX(0 * SCALE);
    gradientView.setLayoutY(0 * SCALE);

    // logo
    Image logoImage = getImage(folderUrl + "logo.png");
    logoView = new ImageView(logoImage);
    logoView.setFitWidth(57.9 * SCALE);
    logoView.setFitHeight(57.9 * SCALE);
    logoView.setLayoutX(30 * SCALE);
    logoView.setLayoutY(30 * SCALE);

    waitUntilText = new Label("");
    waitUntilText.setFont(new Font("Arial", 16 * 2));
    waitUntilText.setLayoutX(111 * SCALE);
    waitUntilText.setLayoutY(297.7 * SCALE);
    waitUntilText.setMaxWidth(1750.3 * SCALE);
    waitUntilText.setMaxHeight(323.9 * SCALE);
    waitUntilText.setWrapText(true);

    // 3
    Image threeImage = getImage(folderUrl + "3.png");
    threeView = new ImageView(threeImage);
    threeView.setFitWidth(333 * SCALE);
    threeView.setFitHeight(519.6 * SCALE);
    threeView.setLayoutX(787.8 * SCALE);
    threeView.setLayoutY(311.3 * SCALE);

    // 2
    Image twoImage = getImage(folderUrl + "2.png");
    twoView = new ImageView(twoImage);
    twoView.setFitWidth(336.2 * SCALE);
    twoView.setFitHeight(510.6 * SCALE);
    twoView.setLayoutX(779.4 * SCALE);
    twoView.setLayoutY(311.3 * SCALE);

    // 1
    Image oneImage = getImage(folderUrl + "1.png");
    oneView = new ImageView(oneImage);
    oneView.setFitWidth(187.3 * SCALE);
    oneView.setFitHeight(510.6 * SCALE);
    oneView.setLayoutX(835.3 * SCALE);
    oneView.setLayoutY(311.3 * SCALE);

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

    waitUntilText.setVisible(true);
    threeView.setVisible(false);
    twoView.setVisible(false);
    oneView.setVisible(false);
  }

  @Override
  public void layoutParts() {
    if (Objects.equals(ConfigManager.getProperty("app.settings.showButtons"), "true")) {
      getChildren().addAll(gradientView, logoView, waitUntilText, threeView, twoView, oneView,
          option3View, option3SelectedView, option3Text, buttonPrimary, buttonSecondary,
          buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(gradientView, logoView, waitUntilText, threeView, twoView, oneView,
          option3View, option3SelectedView, option3Text);
    }
  }

  @Override
  public void setupUiToActionBindings(StateController controller) {
    buttonPrimary.setOnAction(e -> controller.actionPrimary());
    buttonSecondary.setOnAction(e -> controller.actionSecondary());
    buttonTertiary.setOnAction(e -> controller.actionTertiary());
    buttonAck.setOnAction(e -> controller.actionAck());
    switchState = controller::actionEvent;
  }

  @Override
  public void setupModelToUiBindings(StateModel model) {
    onChangeOf(model.synced).execute(((oldValue, newValue) -> {
      if (model.syncState.getValue().equals(SyncState.WaitsForOtherUserToConfirmVS)) {
        createAndStartTimer(newValue);
      } else {
        Platform.runLater(() -> switchState.action());
      }
    }));
    onChangeOf(model.option3Selected).update(option3SelectedView.visibleProperty());

    languageLoader.loadTextToLabel(waitUntilText, TextKeyEnum.WAIT_UNTIL, isPlayerA);
  }

  private void createAndStartTimer(boolean synced) {
    if (!synced) {
      return;
    }
    soundStage.play(Sound.COUNTDOWN);
    Timeline countdownTimeline = new Timeline(
        new KeyFrame(Duration.seconds(1), e -> {
          option3View.setVisible(false);
          option3SelectedView.setVisible(false);

          waitUntilText.setVisible(false);
          threeView.setVisible(true);
        }),
        new KeyFrame(Duration.seconds(2), e -> {
          threeView.setVisible(false);
          twoView.setVisible(true);
        }),
        new KeyFrame(Duration.seconds(3), e -> {
          twoView.setVisible(false);
          oneView.setVisible(true);
        }),
        new KeyFrame(Duration.seconds(4), e -> Platform.runLater(() -> switchState.action()))
    );
    countdownTimeline.setCycleCount(1);
    countdownTimeline.play();
  }

  @FunctionalInterface
  private interface Action {
    void action();
  }
}
