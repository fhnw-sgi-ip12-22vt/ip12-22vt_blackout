package com.blackout.view.gui.state;

import com.blackout.controller.state.StateController;
import com.blackout.model.StateModel;
import com.blackout.util.mvcbase.ViewMixin;
import com.blackout.util.sound.SoundFx;
import com.blackout.util.sound.SoundStage;
import javafx.scene.layout.Pane;

public abstract class StateView extends Pane implements ViewMixin<StateModel, StateController> {
  protected final SoundStage soundStage;
  protected final boolean isPlayerA;

  public StateView(boolean isPlayerA) {
    this.isPlayerA = isPlayerA;
    this.soundStage = isPlayerA ? SoundFx.soundStageA : SoundFx.soundStageB;
  }

  @Override
  public void init(StateController controller) {
    ViewMixin.super.init(controller);
  }
}
