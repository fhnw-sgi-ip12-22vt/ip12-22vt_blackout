package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.language.LanguageLoader;
import com.blackout.util.language.TextKeyEnum;
import com.blackout.util.quiz.Question;
import com.blackout.util.quiz.QuestionSet;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class QuestionView extends StateView {
  private final double SCALE = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
  private Button buttonPrimary;
  private Button buttonSecondary;
  private Button buttonTertiary;
  private Button buttonAck;
  private ImageView gradientView;
  private ImageView option1View;
  private ImageView option1SelectedView;
  private ImageView option2View;
  private ImageView option2SelectedView;
  private ImageView option3View;
  private ImageView option3SelectedView;
  private ImageView atLeastView;
  private Label atLeastText;
  private Label questionNumber;
  private Label questionString;
  private Label answer1String;
  private Label answer2String;
  private Label answer3String;
  private LanguageLoader languageLoader = new LanguageLoader();

  public QuestionView(boolean isPlayerA) {
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

    String folderUrl = "/views/question/";

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

    // option3Selected
    Image option3SelectedImage = getImage(folderUrl + "option3_selected.png");
    option3SelectedView = new ImageView(option3SelectedImage);
    option3SelectedView.setFitWidth(640 * SCALE);
    option3SelectedView.setFitHeight(345 * SCALE);
    option3SelectedView.setLayoutX(1280 * SCALE);
    option3SelectedView.setLayoutY(735 * SCALE);

    // alertView
    Image alertImage = getImage(folderUrl + "at_least.png");
    atLeastView = new ImageView(alertImage);
    atLeastView.setFitWidth(1920 * SCALE);
    atLeastView.setFitHeight(1080 * SCALE);
    atLeastView.setLayoutX(0 * SCALE);
    atLeastView.setLayoutY(0 * SCALE);

    atLeastView.setVisible(false);

    atLeastText = new Label("");
    atLeastText.setFont(new Font("Arial", 16 * 2));
    atLeastText.setLayoutX(111 * SCALE);
    atLeastText.setLayoutY(297.7 * SCALE);
    atLeastText.setMaxWidth(1750.3 * SCALE);
    atLeastText.setMaxHeight(323.9 * SCALE);
    atLeastText.setWrapText(true);

    questionNumber = new Label("");
    questionNumber.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 32 * 2));
    questionNumber.setLayoutX(116 * SCALE);
    questionNumber.setLayoutY(140.4 * SCALE);
    questionNumber.setMaxWidth(844 * SCALE);
    questionNumber.setMaxHeight(123.9 * SCALE);
    questionNumber.setWrapText(true);

    questionString = new Label("");
    questionString.setFont(new Font("Arial", 16 * 2));
    questionString.setLayoutX(111 * SCALE);
    questionString.setLayoutY(297.7 * SCALE);
    questionString.setMaxWidth(1750.3 * SCALE);
    questionString.setMaxHeight(323.9 * SCALE);
    questionString.setWrapText(true);

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
          option2SelectedView, option3View, option3SelectedView, questionNumber, questionString,
          answer1String, answer2String, answer3String, atLeastView, atLeastText, buttonPrimary,
          buttonSecondary, buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(gradientView, option1View, option1SelectedView, option2View,
          option2SelectedView, option3View, option3SelectedView, questionNumber, questionString,
          answer1String, answer2String, answer3String, atLeastView, atLeastText);
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
    onChangeOf(model.option1Selected).update(option1SelectedView.visibleProperty());
    onChangeOf(model.option2Selected).update(option2SelectedView.visibleProperty());
    onChangeOf(model.option3Selected).update(option3SelectedView.visibleProperty());
    onChangeOf(model.alertVisible).update(atLeastView.visibleProperty());
    onChangeOf(model.alertVisible).update(atLeastText.visibleProperty());

    QuestionSet questionSet = model.getQuestionSet();
    Question question = model.getQuestionSet().getQuestion();

    String questionNumberText =
        languageLoader.getText(TextKeyEnum.QUESTION, isPlayerA) +
            " " +
            questionSet.questionNumber.get() +
            " " +
            languageLoader.getText(TextKeyEnum.OF, isPlayerA) +
            " " +
            questionSet.getAmountOfQuestions();

    questionNumber.setText(questionNumberText);
    questionString.setText(question.getQuestionString(isPlayerA));
    answer1String.setText(question.getAnswerStrings(isPlayerA).get(0));
    answer2String.setText(question.getAnswerStrings(isPlayerA).get(1));
    answer3String.setText(question.getAnswerStrings(isPlayerA).get(2));

    languageLoader.loadTextToLabel(atLeastText, TextKeyEnum.AT_LEAST, isPlayerA);
  }
}
