package com.blackout.view.gui.state;

import com.blackout.controller.state.ConfigController;
import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.sound.SoundFx;
import java.util.Objects;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ConfigView extends StateView {
  private Button saveConfigButton;
  private Button resetConfigButton;
  private TextArea configTextArea;
  private Button reloadAudioOutputList;
  private TextArea audioOutputTextArea;
  private Button startSystemCheck;
  private Button stopSystemCheck;
  private Button buttonHeadsetA;
  private Button buttonSpeakerA;
  private Button buttonHeadsetB;
  private Button buttonSpeakerB;
  private Button leaveConfigButton;

  public ConfigView(boolean isPlayerA) {
    super(isPlayerA);
  }

  @Override
  public void initializeParts() {
    Label configLabel = new Label("Config");
    configLabel.getStyleClass().add("config-label");

    leaveConfigButton = new Button("Close Application");
    leaveConfigButton.getStyleClass().add("config-button");

    GridPane configEditorGridPane = initConfigGridPane();
    GridPane audioOutputGridPane = initAudioOutputView();
    GridPane systemCheckGridPane = initSystemCheckGridPane();

    VBox contentBox = new VBox();
    contentBox.setMinWidth(1920);
    contentBox.setMinHeight(1080);
    contentBox.setSpacing(10);
    contentBox.setPadding(new Insets(10));
    contentBox.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/config-view.css")).toExternalForm());
    contentBox.getChildren()
        .addAll(configLabel, leaveConfigButton, configEditorGridPane,
            audioOutputGridPane, systemCheckGridPane);

    getChildren().add(contentBox);
  }

  @Override
  public void init(StateController controller) {
    super.init(controller);
    if (!(controller instanceof ConfigController configController)) {
      return;
    }
    configController.loadConfigFile();
    configController.reloadAudioOutputList();
  }

  @Override
  public void layoutParts() {
  }

  @Override
  public void setupModelToUiBindings(StateModel model) {
    onChangeOf(model.audioOutputs)
        .execute((oldValue, newValue) -> audioOutputTextArea.setText(newValue));
    onChangeOf(model.configText)
        .execute(((oldValue, newValue) -> configTextArea.setText(newValue)));
  }

  @Override
  public void setupUiToActionBindings(StateController controller) {
    if (!(controller instanceof ConfigController configController)) {
      return;
    }

    leaveConfigButton.setOnAction(e -> {
      SoundFx.soundStageA.close();
      SoundFx.soundStageB.close();
      Platform.exit();
    });
    saveConfigButton.setOnAction(e -> configController.saveConfigFile(configTextArea.getText()));
    resetConfigButton.setOnAction(event -> configController.resetConfigFile());
    reloadAudioOutputList.setOnAction(e -> configController.reloadAudioOutputList());
    startSystemCheck.setOnAction(e -> {
      buttonHeadsetA.setDisable(false);
      buttonSpeakerA.setDisable(false);
      buttonHeadsetB.setDisable(false);
      buttonSpeakerB.setDisable(false);
      stopSystemCheck.setDisable(false);
      configController.startSystemCheck();
    });
    stopSystemCheck.setOnAction(e -> {
      buttonHeadsetA.setDisable(true);
      buttonSpeakerA.setDisable(true);
      buttonHeadsetB.setDisable(true);
      buttonSpeakerB.setDisable(true);
      stopSystemCheck.setDisable(true);
      configController.stopSystemCheck();
    });
    buttonHeadsetA.setOnAction(e -> configController.checkHeadsetA());
    buttonSpeakerA.setOnAction(e -> configController.checkSpeakerA());
    buttonHeadsetB.setOnAction(e -> configController.checkHeadsetB());
    buttonSpeakerB.setOnAction(e -> configController.checkSpeakerB());
  }


  private GridPane initSystemCheckGridPane() {
    Label systemCheckLabel = new Label("Systemcheck");
    systemCheckLabel.getStyleClass().add("config-label");

    startSystemCheck = new Button("Start Systemcheck");
    startSystemCheck.getStyleClass().add("config-button");

    stopSystemCheck = new Button("Stop Systemcheck");
    stopSystemCheck.getStyleClass().add("config-button");
    stopSystemCheck.setDisable(true);

    Label info = new Label(
        "Testen der Buttons: " + System.lineSeparator()
            + "-> Info: Führen Sie die unteren Instruktion für jeden Button am System aus!" +
            System.lineSeparator()
            +
            "-> 1. Drücken sie den Button (Kein Ego-Shooter Finger, sonst erkennt das System die Interaktion nicht) -> LED leuchtet" +
            System.lineSeparator()
            + "-> 2. Drücken sie den Button nochmals -> LED leuchtet nicht" + System.lineSeparator()
            + "-> Wenn alles Funktioniert hat, erkenn das System die Buttons fehlerfrei!");
    info.setPadding(new Insets(20));

    buttonHeadsetA = new Button("Check Headset A");
    buttonHeadsetA.getStyleClass().add("config-button");
    buttonHeadsetA.setDisable(true);

    buttonSpeakerA = new Button("Check Speaker A");
    buttonSpeakerA.getStyleClass().add("config-button");
    buttonSpeakerA.setDisable(true);

    buttonHeadsetB = new Button("Check Headset B");
    buttonHeadsetB.getStyleClass().add("config-button");
    buttonHeadsetB.setDisable(true);

    buttonSpeakerB = new Button("Check Speaker B");
    buttonSpeakerB.getStyleClass().add("config-button");
    buttonSpeakerB.setDisable(true);

    GridPane systemCheckPane = new GridPane();
    systemCheckPane.setHgap(10);
    systemCheckPane.setVgap(10);
    systemCheckPane.add(systemCheckLabel, 0, 0, 1, 1);
    systemCheckPane.add(startSystemCheck, 0, 1);
    systemCheckPane.add(stopSystemCheck, 1, 1);
    systemCheckPane.add(info, 0, 2, 1, 1);
    systemCheckPane.add(buttonHeadsetA, 0, 3);
    systemCheckPane.add(buttonSpeakerA, 1, 3);
    systemCheckPane.add(buttonHeadsetB, 0, 4);
    systemCheckPane.add(buttonSpeakerB, 1, 4);
    return systemCheckPane;
  }

  private GridPane initConfigGridPane() {
    Label configLabel = new Label("config.properties");
    configLabel.getStyleClass().add("config-label");

    configTextArea = new TextArea();
    configTextArea.setMinWidth(1000);
    configTextArea.setWrapText(true);
    configTextArea.getStyleClass().add("config-text-area");

    resetConfigButton = new Button("Reset to default");
    resetConfigButton.getStyleClass().add("config-button");

    saveConfigButton = new Button("Save");
    saveConfigButton.getStyleClass().add("config-button");

    GridPane configGridPane = new GridPane();
    configGridPane.setHgap(10);
    configGridPane.setVgap(10);
    configGridPane.add(configLabel, 0, 0, 1, 1);
    configGridPane.add(configTextArea, 0, 1, 1, 1);
    configGridPane.add(resetConfigButton, 0, 2, 1, 1);
    configGridPane.add(saveConfigButton, 0, 3, 1, 1);
    return configGridPane;
  }

  private GridPane initAudioOutputView() {
    Label audioOutputLabel = new Label("Audio Outputs:");
    audioOutputLabel.getStyleClass().add("test-label");

    audioOutputTextArea = new TextArea();
    audioOutputTextArea.setMinWidth(1000);
    audioOutputTextArea.setWrapText(true);
    audioOutputTextArea.setEditable(false);
    audioOutputTextArea.setMaxHeight(75);
    audioOutputTextArea.getStyleClass().add("test-text-area");

    reloadAudioOutputList = new Button("Refresh audio outputs");
    reloadAudioOutputList.getStyleClass().add("config-button");

    GridPane audioOutputGrid = new GridPane();
    audioOutputGrid.setHgap(10);
    audioOutputGrid.setVgap(10);
    audioOutputGrid.add(audioOutputLabel, 0, 0, 1, 1);
    audioOutputGrid.add(audioOutputTextArea, 0, 1, 1, 1);
    audioOutputGrid.add(reloadAudioOutputList, 0, 2, 1, 1);
    return audioOutputGrid;
  }
}
