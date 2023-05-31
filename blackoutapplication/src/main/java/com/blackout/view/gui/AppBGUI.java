package com.blackout.view.gui;

import com.blackout.controller.AppController;
import com.blackout.model.AppModel;
import com.blackout.util.mvcbase.ViewMixin;
import javafx.scene.layout.Pane;

public class AppBGUI extends Pane implements ViewMixin<AppModel, AppController> {
  private AppController controller;

  @Override
  public void init(AppController controller) {
    ViewMixin.super.init(controller);
    this.controller = controller;
  }

  @Override
  public void initializeParts() {
  }

  @Override
  public void layoutParts() {
  }

  @Override
  public void setupModelToUiBindings(AppModel model) {
    onChangeOf(model.viewB).execute((oldValue, newValue) -> {
      if (controller.isIdleTimerRequiredB()) {
        controller.startIdleTimerB();
      }
      getChildren().clear();
      getChildren().add(newValue);
    });
  }
}
