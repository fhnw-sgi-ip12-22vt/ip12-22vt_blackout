package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.language.LanguageLoader;
import com.blackout.util.language.TextKeyEnum;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ModeView extends StateView {
  private final double SCALE = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
  private Button buttonPrimary;
  private Button buttonSecondary;
  private Button buttonTertiary;
  private Button buttonAck;
  private ImageView gradientView;
  private ImageView logoView;
  private ImageView option1View;
  private Label option1Text;
  private ImageView option1SelectedView;
  private ImageView option3View;
  private Label option3Text;
  private Label questionString;
  private ImageView option3SelectedView;
  private LanguageLoader languageLoader = new LanguageLoader();

  public ModeView(boolean isPlayerA) {
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

    String folderUrl = "/views/mode/";

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

    questionString = new Label("");
    questionString.setFont(new Font("Arial", 16 * 2));
    questionString.setLayoutX(111 * SCALE);
    questionString.setLayoutY(297.7 * SCALE);
    questionString.setMaxWidth(1750.3 * SCALE);
    questionString.setMaxHeight(323.9 * SCALE);
    questionString.setWrapText(true);
  }

  @Override
  public void layoutParts() {
    if (Objects.equals(ConfigManager.getProperty("app.settings.showButtons"), "true")) {
      getChildren().addAll(gradientView, logoView, option1View, option1SelectedView, option1Text,
          option3View, option3SelectedView, option3Text, questionString, buttonPrimary,
          buttonSecondary, buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(gradientView, logoView, option1View, option1SelectedView, option1Text,
          option3View, option3SelectedView, option3Text, questionString);
    }
  }

  @Override
  public void setupUiToActionBindings(StateController controller) {
    buttonPrimary.setOnAction(e -> controller.actionPrimary());
    buttonSecondary.setOnAction(e -> controller.actionSecondary());
    buttonTertiary.setOnAction(e -> controller.actionTertiary());
    buttonAck.setOnAction(e -> controller.actionAck());
  }

  @Override
  public void setupModelToUiBindings(StateModel model) {
    super.setupModelToUiBindings(model);
    onChangeOf(model.option1Selected).update(option1SelectedView.visibleProperty());
    onChangeOf(model.option3Selected).update(option3SelectedView.visibleProperty());

    languageLoader.loadTextToLabel(option1Text, TextKeyEnum.RANDOM_MODE, isPlayerA);
    languageLoader.loadTextToLabel(option3Text, TextKeyEnum.FEEDBACK_MODE, isPlayerA);
    languageLoader.loadTextToLabel(questionString, TextKeyEnum.RANDOM_OR_FEEDBACK, isPlayerA);
  }
}
