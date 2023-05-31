package com.blackout;

import com.blackout.controller.AppController;
import com.blackout.model.AppModel;
import com.blackout.util.audio.AudioConfig;
import com.blackout.util.config.ConfigManager;
import com.blackout.util.dialog.DialogHelper;
import com.blackout.util.pi4j.Pi4JContext;
import com.blackout.util.sound.SoundFx;
import com.blackout.util.update.ConfigPropertiesChecker;
import com.blackout.util.update.LanguageJsonUpdater;
import com.blackout.util.update.QuestionsJsonUpdater;
import com.blackout.util.update.RoomsJsonUpdater;
import com.blackout.view.gui.AppAGUI;
import com.blackout.view.gui.AppBGUI;
import com.blackout.view.pui.AppPUI;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AppStarter extends Application {
  private double width;
  private double height;
  private AppController controller;
  private AppPUI pui;
  private Stage stageA;
  private Stage stageB;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stageA) {
    ConfigPropertiesChecker.check();
    LanguageJsonUpdater.update();
    QuestionsJsonUpdater.update();
    RoomsJsonUpdater.update();
    SoundFx.soundStageA.init(AudioConfig.getAudioConfigA());
    SoundFx.soundStageB.init(AudioConfig.getAudioConfigB());
    double scale = Double.parseDouble(ConfigManager.getProperty("app.settings.scale"));
    width = 1920 * scale;
    height = 1080 * scale;
    AppModel model = new AppModel();
    controller = new AppController(model);
    pui = new AppPUI(controller, Pi4JContext.createContext());
    this.stageA = stageA;
    stageB = new Stage();
    startStageA();
    startStageB();
  }

  @Override
  public void stop() {
    controller.shutdown();
    pui.shutdown();
  }

  private void startStageA() {
    var viewA = new AppAGUI();
    viewA.init(controller);
    startStage(stageA, viewA);
  }

  private void startStageB() {
    var viewB = new AppBGUI();
    viewB.init(controller);
    startStage(stageB, viewB);
  }

  private void startStage(Stage stage, Pane view) {
    boolean isScreenA = stage == stageA;
    stage.setScene(new Scene(view, width, height));
    stage.setOnCloseRequest(this::handleCloseRequest);
    String title =
        ConfigManager.getProperty("app.settings.title") + " Screen " + (isScreenA ? "A" : "B");
    stage.setTitle(title);

    // move stage to the correct screen
    // from https://stackoverflow.com/a/39450735
    var screens = Screen.getScreens().toArray(Screen[]::new);
    if (screens.length == 2) {
      Rectangle2D bounds = screens[(isScreenA ? 0 : 1)].getVisualBounds();
      stage.setX(bounds.getMinX());
      stage.setY(bounds.getMinY());
    }

    boolean isFullScreen =
        Boolean.parseBoolean(ConfigManager.getProperty("app.settings.fullscreen"));
    stage.setFullScreen(isFullScreen);
    stage.show();
  }

  private void handleCloseRequest(WindowEvent event) {
    if (DialogHelper.confirmMessage(ConfigManager.getProperty("app.settings.title"),
        "Möchtest du Blackout wirklich beenden?")) {
      stageA.close();
      stageB.close();
    } else {
      event.consume();
    }
  }
}
