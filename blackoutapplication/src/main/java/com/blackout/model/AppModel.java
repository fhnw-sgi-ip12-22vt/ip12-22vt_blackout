package com.blackout.model;

import com.blackout.util.mvcbase.ObservableValue;
import com.blackout.view.gui.state.StateView;

public class AppModel {
  public final ObservableValue<StateView> viewA = new ObservableValue<>(new StateView(false) {
    @Override
    public void layoutParts() {

    }

    @Override
    public void initializeParts() {

    }
  });
  public final ObservableValue<StateView> viewB = new ObservableValue<>(new StateView(false) {
    @Override
    public void layoutParts() {

    }

    @Override
    public void initializeParts() {

    }
  });

  // first input
  public final ObservableValue<Boolean> ledA = new ObservableValue<>(false);
  public final ObservableValue<Boolean> ledB = new ObservableValue<>(false);
  public final ObservableValue<Boolean> ledC = new ObservableValue<>(false);
  public final ObservableValue<Boolean> ledAckA = new ObservableValue<>(false);

  // second input
  public final ObservableValue<Boolean> ledZ = new ObservableValue<>(false);
  public final ObservableValue<Boolean> ledX = new ObservableValue<>(false);
  public final ObservableValue<Boolean> ledY = new ObservableValue<>(false);
  public final ObservableValue<Boolean> ledAckB = new ObservableValue<>(false);
}
