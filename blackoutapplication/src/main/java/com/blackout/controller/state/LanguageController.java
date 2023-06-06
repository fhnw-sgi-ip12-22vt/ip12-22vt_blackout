package com.blackout.controller.state;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.util.config.ConfigManager;
import com.blackout.view.gui.state.LanguageView;
import com.blackout.view.gui.state.StateView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class LanguageController extends StateController {
  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public LanguageController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.reset();
  }

  protected void actionPrimaryOverride() {
    super.actionPrimaryOverride();
    previousLanguage();
    model.option1Selected.setValue(true);
    model.option3Selected.setValue(false);
    PauseTransition delay = new PauseTransition(Duration.seconds(0.25));
    delay.setOnFinished((e) -> {
      model.option1Selected.setValue(false);
    });
    delay.play();
  }

  @Override
  protected void actionTertiaryOverride() {
    super.actionTertiaryOverride();
    nextLanguage();
    model.option3Selected.setValue(true);
    model.option1Selected.setValue(false);
    PauseTransition delay = new PauseTransition();
    delay.setOnFinished((e) -> {
      model.option3Selected.setValue(false);
    });
    delay.play();
  }

  @Override
  protected void actionAckOverride() {
    super.actionAckOverride();
    stateMachine.changeState(new IdleController(stateMachine, model));
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new LanguageView(isPlayerA);
    view.init(this);
    return view;
  }

  private void nextLanguage() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    // Todo: remove ugly workaround
    String configLanguageProperty;

    if (isPlayerA) {
      configLanguageProperty = "app.settings.language.A";
    } else {
      configLanguageProperty = "app.settings.language.B";
    }

    switch (model.language.getValue()) {
      case "Deutsch" -> {
        ConfigManager.setProperty(configLanguageProperty, "en");
        model.language.setValue("English");
      }
      case "English" -> {
        ConfigManager.setProperty(configLanguageProperty, "fr");
        model.language.setValue("Français");
      }
      case "Français" -> {
        ConfigManager.setProperty(configLanguageProperty, "it");
        model.language.setValue("Italiano");
      }
      case "Italiano" -> {
        ConfigManager.setProperty(configLanguageProperty, "de");
        model.language.setValue("Deutsch");
      }
      default -> {
        ConfigManager.setProperty(configLanguageProperty, "de");
        model.language.setValue("Deutsch");
      }
    }
  }

  private void previousLanguage() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    // Todo: remove ugly workaround
    String configLanguageProperty;

    if (isPlayerA) {
      configLanguageProperty = "app.settings.language.A";
    } else {
      configLanguageProperty = "app.settings.language.B";
    }

    switch (model.language.getValue()) {
      case "Deutsch" -> {
        ConfigManager.setProperty(configLanguageProperty, "it");
        model.language.setValue("Italiano");
      }
      case "Italiano" -> {
        ConfigManager.setProperty(configLanguageProperty, "fr");
        model.language.setValue("Français");
      }
      case "Français" -> {
        ConfigManager.setProperty(configLanguageProperty, "en");
        model.language.setValue("English");
      }
      case "English" -> {
        ConfigManager.setProperty(configLanguageProperty, "de");
        model.language.setValue("Deutsch");
      }
      default -> {
        ConfigManager.setProperty(configLanguageProperty, "de");
        model.language.setValue("Deutsch");
      }
    }
  }
}