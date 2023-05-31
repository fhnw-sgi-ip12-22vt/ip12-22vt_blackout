package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.language.LanguageLoader;
import com.blackout.util.language.TextKeyEnum;
import com.blackout.util.quiz.Question;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ResultView extends StateView {
  private final double SCALE = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
  private Button buttonPrimary;
  private Button buttonSecondary;
  private Button buttonTertiary;
  private Button buttonAck;
  private ImageView gradientView;
  private ImageView option1View;
  private Label option1Text;
  private ImageView option1SelectedView;
  private ImageView option2View;
  private Label option2Text;
  private ImageView option2SelectedView;
  private ImageView option3View;
  private Label option3Text;
  private ImageView option3SelectedView;
  private ImageView correct1View;
  private ImageView correct2View;
  private ImageView correct3View;
  private Label scoreString;
  private Label explanationString;
  private Label answer1String;
  private Label answer2String;
  private Label answer3String;
  private LanguageLoader languageLoader = new LanguageLoader();

  public ResultView(boolean isPlayerA) {
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

    String folderUrl = "/views/result/";

    // gradient
    Image gradientImage = getImage(folderUrl + "gradient.png");
    gradientView = new ImageView(gradientImage);
    gradientView.setFitWidth(1920 * SCALE);
    gradientView.setFitHeight(1080 * SCALE);
    gradientView.setLayoutX(0 * SCALE);
    gradientView.setLayoutY(0 * SCALE);

    // option1
    Image option1Image = getImage(folderUrl + "option1.png");
    option1View = new ImageView(option1Image);
    option1View.setFitWidth(640 * SCALE);
    option1View.setFitHeight(345 * SCALE);
    option1View.setLayoutX(0 * SCALE);
    option1View.setLayoutY(735 * SCALE);

    option1Text = new Label();
    option1Text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 8 * 2));
    option1Text.setLayoutX(110.1 * SCALE);
    option1Text.setLayoutY(805.1 * SCALE);
    option1Text.setMaxWidth(490.4 * SCALE);
    option1Text.setWrapText(true);

    // option1Selected
    Image option1SelectedImage = getImage(folderUrl + "option1_selected.png");
    option1SelectedView = new ImageView(option1SelectedImage);
    option1SelectedView.setFitWidth(640 * SCALE);
    option1SelectedView.setFitHeight(345 * SCALE);
    option1SelectedView.setLayoutX(0 * SCALE);
    option1SelectedView.setLayoutY(735 * SCALE);

    // option2
    Image option2Image = getImage(folderUrl + "option2.png");
    option2View = new ImageView(option2Image);
    option2View.setFitWidth(640 * SCALE);
    option2View.setFitHeight(345 * SCALE);
    option2View.setLayoutX(640 * SCALE);
    option2View.setLayoutY(735 * SCALE);

    option2Text = new Label();
    option2Text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 8 * 2));
    option2Text.setLayoutX(766.4 * SCALE);
    option2Text.setLayoutY(805.1 * SCALE);
    option2Text.setMaxWidth(490.4 * SCALE);
    option2Text.setWrapText(true);

    // option2Selected
    Image option2SelectedImage = getImage(folderUrl + "option2_selected.png");
    option2SelectedView = new ImageView(option2SelectedImage);
    option2SelectedView.setFitWidth(640 * SCALE);
    option2SelectedView.setFitHeight(345 * SCALE);
    option2SelectedView.setLayoutX(640 * SCALE);
    option2SelectedView.setLayoutY(735 * SCALE);

    // option3
    Image option3Image = getImage(folderUrl + "option3.png");
    option3View = new ImageView(option3Image);
    option3View.setFitWidth(640 * SCALE);
    option3View.setFitHeight(345 * SCALE);
    option3View.setLayoutX(1280 * SCALE);
    option3View.setLayoutY(735 * SCALE);

    option3Text = new Label();
    option3Text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 8 * 2));
    option3Text.setLayoutX(1408.5 * SCALE);
    option3Text.setLayoutY(805.1 * SCALE);
    option3Text.setMaxWidth(490.4 * SCALE);
    option3Text.setWrapText(true);

    // option3Selected
    Image option3SelectedImage = getImage(folderUrl + "option3_selected.png");
    option3SelectedView = new ImageView(option3SelectedImage);
    option3SelectedView.setFitWidth(640 * SCALE);
    option3SelectedView.setFitHeight(345 * SCALE);
    option3SelectedView.setLayoutX(1280 * SCALE);
    option3SelectedView.setLayoutY(735 * SCALE);

    // correct1
    Image correct1Image = getImage(folderUrl + "correct.png");
    correct1View = new ImageView(correct1Image);
    correct1View.setFitWidth(640 * SCALE);
    correct1View.setFitHeight(38.9 * SCALE);
    correct1View.setLayoutX(0 * SCALE);
    correct1View.setLayoutY(735 * SCALE);

    // correct2
    Image correctImage = getImage(folderUrl + "correct.png");
    correct2View = new ImageView(correctImage);
    correct2View.setFitWidth(640 * SCALE);
    correct2View.setFitHeight(38.9 * SCALE);
    correct2View.setLayoutX(640 * SCALE);
    correct2View.setLayoutY(735 * SCALE);

    // correct3
    Image correct2Image = getImage(folderUrl + "correct.png");
    correct3View = new ImageView(correct2Image);
    correct3View.setFitWidth(640 * SCALE);
    correct3View.setFitHeight(38.9 * SCALE);
    correct3View.setLayoutX(1280 * SCALE);
    correct3View.setLayoutY(735 * SCALE);

    scoreString = new Label("");
    scoreString.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 32 * 2));
    scoreString.setLayoutX(116 * SCALE);
    scoreString.setLayoutY(140.4 * SCALE);
    scoreString.setMaxWidth(844 * SCALE);
    scoreString.setMaxHeight(123.9 * SCALE);
    scoreString.setWrapText(true);

    explanationString = new Label("");
    explanationString.setFont(new Font("Arial", 16 * 2));
    explanationString.setLayoutX(111 * SCALE);
    explanationString.setLayoutY(297.7 * SCALE);
    explanationString.setMaxWidth(1750.3 * SCALE);
    explanationString.setMaxHeight(323.9 * SCALE);
    explanationString.setWrapText(true);

    answer1String = new Label("");
    answer1String.setFont(new Font("Arial", 8 * 2));
    answer1String.setLayoutX(111 * SCALE);
    answer1String.setLayoutY(805.1 * SCALE);
    answer1String.setMaxWidth(490.4 * SCALE);
    answer1String.setMaxHeight(231.2 * SCALE);
    answer1String.setWrapText(true);

    answer2String = new Label("");
    answer2String.setFont(new Font("Arial", 8 * 2));
    answer2String.setLayoutX(766.4 * SCALE);
    answer2String.setLayoutY(805.1 * SCALE);
    answer2String.setMaxWidth(490.4 * SCALE);
    answer2String.setMaxHeight(231.2 * SCALE);
    answer2String.setWrapText(true);

    answer3String = new Label("");
    answer3String.setFont(new Font("Arial", 8 * 2));
    answer3String.setLayoutX(1408.5 * SCALE);
    answer3String.setLayoutY(805.1 * SCALE);
    answer3String.setMaxWidth(490.4 * SCALE);
    answer3String.setMaxHeight(231.2 * SCALE);
    answer3String.setWrapText(true);
  }

  @Override
  public void layoutParts() {
    if (Objects.equals(ConfigManager.getProperty("app.settings.showButtons"), "true")) {
      getChildren().addAll(gradientView, option1View, option1SelectedView, option2View,
          option2SelectedView, option3View, option3SelectedView, correct1View, correct2View,
          correct3View, scoreString, explanationString, answer1String, answer2String, answer3String,
          buttonPrimary, buttonSecondary, buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(gradientView, option1View, option1SelectedView, option2View,
          option2SelectedView, option3View, option3SelectedView, correct1View, correct2View,
          correct3View, scoreString, explanationString, answer1String, answer2String,
          answer3String);
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
    Question question = model.getQuestionSet().getQuestion();

    option1SelectedView.setVisible(question.getAnswer().contains(0));
    option2SelectedView.setVisible(question.getAnswer().contains(1));
    option3SelectedView.setVisible(question.getAnswer().contains(2));

    correct1View.setVisible(question.getSolution().contains(0));
    correct2View.setVisible(question.getSolution().contains(1));
    correct3View.setVisible(question.getSolution().contains(2));

    switch (question.getPoints()) {
      case 3 -> scoreString.setText(languageLoader.getText(TextKeyEnum.SCORE_3, isPlayerA));
      case 2 -> scoreString.setText(languageLoader.getText(TextKeyEnum.SCORE_2, isPlayerA));
      case 1, 0 -> scoreString.setText(languageLoader.getText(TextKeyEnum.SCORE_1, isPlayerA));
      default -> scoreString.setText(languageLoader.getText(TextKeyEnum.UNKNOWN, isPlayerA));
    }

    explanationString.setText(question.getExplanation(isPlayerA));
    answer1String.setText(question.getAnswerStrings(isPlayerA).get(0));
    answer2String.setText(question.getAnswerStrings(isPlayerA).get(1));
    answer3String.setText(question.getAnswerStrings(isPlayerA).get(2));
  }
}
