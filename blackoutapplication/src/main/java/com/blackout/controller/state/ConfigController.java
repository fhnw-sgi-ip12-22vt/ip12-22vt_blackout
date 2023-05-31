package com.blackout.controller.state;

import static java.util.stream.Collectors.groupingBy;

import com.blackout.controller.StateMachine;
import com.blackout.model.ConsoleTyp;
import com.blackout.model.StateModel;
import com.blackout.util.audio.AudioOutput;
import com.blackout.util.exception.BlackoutException;
import com.blackout.util.sound.SoundFx;
import com.blackout.util.sound.Sound;
import com.blackout.view.gui.state.ConfigView;
import com.blackout.view.gui.state.StateView;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ConfigController extends StateController {

  private static final String CONFIG_FILE = "db/config.properties";
  private static final String DEFAULT_FILE = "db/default.properties";

  /**
   * Controller needs a Model.
   *
   * @param model Model managed by this Controller
   */
  public ConfigController(StateMachine stateMachine, StateModel model) {
    super(stateMachine, model);
    model.reset();
  }

  public void loadConfigFile() {
    String text = "";
    try {
      text = Files.readString(Paths.get(CONFIG_FILE), StandardCharsets.ISO_8859_1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    model.configText.setValue(text);
  }

  public void saveConfigFile(String content) {
    try {
      Path targetPath = Paths.get(CONFIG_FILE);
      Files.writeString(targetPath, content, StandardCharsets.ISO_8859_1);
      PauseTransition delay = new PauseTransition(Duration.seconds(1));
      delay.play();
    } catch (IOException e) {
      throw new BlackoutException("Error: unknown");
    }
  }

  public void resetConfigFile() {
    try {
      Path sourcePath = Paths.get(DEFAULT_FILE);
      Path targetPath = Paths.get(CONFIG_FILE);
      Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
      loadConfigFile();
    } catch (IOException ex) {
      throw new BlackoutException("exception.unknown");
    }
  }

  public void reloadAudioOutputList() {
    StringBuilder builder = new StringBuilder();
    Map<Object, List<String>>
        availableMixers = Arrays.stream(AudioOutput.getAvailableMixers())
        .collect(groupingBy(x -> x));
    for (Map.Entry<Object, List<String>> mixerGroup : availableMixers.entrySet()) {
      for (int i = 0; i < mixerGroup.getValue().size(); i++) {
        builder
            .append("AUDIO DEVICE: \"")
            .append(mixerGroup.getValue().get(i))
            .append("\"")
            .append(", AUDIO DEVICE INDEX: \"")
            .append(i)
            .append("\"")
            .append(System.lineSeparator());
      }
    }
    model.audioOutputs.setValue(builder.toString());
  }

  public void startSystemCheck() {
    stateMachine.startSystemCheck();
  }

  public void stopSystemCheck() {
    stateMachine.stopSystemCheck();
  }

  public void checkHeadsetA() {
    if (!stateMachine.isSystemCheckRunning()) {
      return;
    }
    SoundFx.soundStageA.stopAll();
    SoundFx.soundStageB.stopAll();
    SoundFx.soundStageA.switchToHeadset();
    SoundFx.soundStageA.play(Sound.BUTTON);
  }

  public void checkSpeakerA() {
    if (!stateMachine.isSystemCheckRunning()) {
      return;
    }
    SoundFx.soundStageA.stopAll();
    SoundFx.soundStageB.stopAll();
    SoundFx.soundStageA.switchToSpeaker();
    SoundFx.soundStageA.play(Sound.BUTTON);
  }

  public void checkHeadsetB() {
    if (!stateMachine.isSystemCheckRunning()) {
      return;
    }
    SoundFx.soundStageA.stopAll();
    SoundFx.soundStageB.stopAll();
    SoundFx.soundStageB.switchToHeadset();
    SoundFx.soundStageB.play(Sound.BUTTON);
  }

  public void checkSpeakerB() {
    if (!stateMachine.isSystemCheckRunning()) {
      return;
    }
    SoundFx.soundStageA.stopAll();
    SoundFx.soundStageB.stopAll();
    SoundFx.soundStageB.switchToSpeaker();
    SoundFx.soundStageB.play(Sound.BUTTON);
  }

  @Override
  public StateView getView() {
    boolean isPlayerA = stateMachine.getConsoleType() == ConsoleTyp.A;
    var view = new ConfigView(isPlayerA);
    view.init(this);
    return view;
  }
}
