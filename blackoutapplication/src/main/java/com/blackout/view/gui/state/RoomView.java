package com.blackout.view.gui.state;

import static com.blackout.util.rooms.RoomReader.readRoomsFromFile;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.image.ImageManager;
import com.blackout.util.language.LanguageLoader;
import com.blackout.util.language.TextKeyEnum;
import com.blackout.util.rooms.Room;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class RoomView extends StateView {
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
  private ImageView option3SelectedView;
  private ListView<Room> roomList = new ListView<>();
  private LanguageLoader languageLoader = new LanguageLoader();

  public RoomView(boolean isPlayerA) {
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

    String folderUrl = "/views/language/";

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
    logoView.setFitWidth(575.3 * SCALE);
    logoView.setFitHeight(575.3 * SCALE);
    logoView.setLayoutX(1294.2 * SCALE);
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

    // roomList
    roomList = new ListView<>();
    roomList.setPrefWidth(1080 * SCALE);
    roomList.setLayoutX(50);
    roomList.setLayoutY(100);

    // Styling
    String mainColor = "#ffd200";
    roomList.setStyle(
        "-fx-background-color: transparent; " +
            "-fx-control-inner-background: transparent; " +
            "-fx-text-fill: black; " +
            "-fx-font-size: 32px; " +
            "-fx-selection-bar-text: black; " +
            "-fx-focus-color: transparent; " +
            "-fx-faint-focus-color: transparent;" +
            "-fx-selection-bar: " + mainColor + "; " +
            "-fx-cell-background-color: transparent;" +
            "-fx-background-insets: 0, 0, 0, 0;"
    );

    roomList.setItems(readRoomsFromFile("db/rooms.json"));
  }

  @Override
  public void layoutParts() {
    if (Objects.equals(ConfigManager.getProperty("app.settings.showButtons"), "true")) {
      getChildren().addAll(gradientView, logoView, roomList, option1View, option1SelectedView,
          option1Text,
          option3View, option3SelectedView, option3Text, buttonPrimary, buttonSecondary,
          buttonTertiary, buttonAck);
    } else {
      getChildren().addAll(gradientView, logoView, roomList, option1View, option1SelectedView,
          option1Text,
          option3View, option3SelectedView, option3Text);
    }
  }

  public void nextRoom() {
    int currentIndex = roomList.getSelectionModel().getSelectedIndex();
    int nextIndex = currentIndex + 1;

    if (nextIndex < roomList.getItems().size()) {
      roomList.getSelectionModel().select(nextIndex);
      roomList.scrollTo(nextIndex);
    } else {
      roomList.getSelectionModel().select(currentIndex);
    }
  }

  public void previousRoom() {
    int currentIndex = roomList.getSelectionModel().getSelectedIndex();
    int previousIndex = currentIndex - 1;

    if (previousIndex >= 0) {
      roomList.getSelectionModel().select(previousIndex);
      roomList.scrollTo(previousIndex);
    } else {
      roomList.getSelectionModel().select(currentIndex);
    }
  }

  @Override
  public void setupUiToActionBindings(StateController controller) {
    buttonPrimary.setOnAction(e -> {
      previousRoom();
      controller.model.setQuestionSet(roomList.getSelectionModel().getSelectedItem().getRoomId());
      controller.actionPrimary();
    });
    buttonSecondary.setOnAction(e -> controller.actionSecondary());
    buttonTertiary.setOnAction(e -> {
      nextRoom();
      controller.model.setQuestionSet(roomList.getSelectionModel().getSelectedItem().getRoomId());
      controller.actionTertiary();
    });
    buttonAck.setOnAction(e -> controller.actionAck());
  }

  @Override
  public void setupModelToUiBindings(StateModel model) {
    super.setupModelToUiBindings(model);
    onChangeOf(model.option1Selected).update(option1SelectedView.visibleProperty());
    onChangeOf(model.option3Selected).update(option3SelectedView.visibleProperty());

    languageLoader.loadTextToLabel(option1Text, TextKeyEnum.UP, isPlayerA);
    languageLoader.loadTextToLabel(option3Text, TextKeyEnum.DOWN, isPlayerA);
  }
}
