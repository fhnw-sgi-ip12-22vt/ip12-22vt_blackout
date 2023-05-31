package com.blackout.util.sound;

import com.blackout.util.audio.AudioInput;

/**
 * The sounds enum is an enumeration of all available sound of the application.
 * Furthermore, the path of the resource and the default audio input index are stored in the enum.
 * * The audio input index is the index of the audio input on which the sound should be played.
 */
public enum Sound {
  BUTTON("button", AudioInput.Tertiary),
  IDLE_EN("idle_en", AudioInput.Primary),
  IDLE_DE("idle_de", AudioInput.Primary),
  IDLE_FR("idle_fr", AudioInput.Primary),
  IDLE_IT("idle_it", AudioInput.Primary),
  COUNTDOWN("countdown", AudioInput.Secondary),
  VARIANTS_EN("variants_en", AudioInput.Primary),
  VARIANTS_DE("variants_de", AudioInput.Primary),
  VARIANTS_FR("variants_fr", AudioInput.Primary),
  VARIANTS_IT("variants_it", AudioInput.Primary),
  END_EN("end_en", AudioInput.Secondary),
  END_DE("end_de", AudioInput.Secondary),
  END_FR("end_fr", AudioInput.Secondary),
  END_IT("end_it", AudioInput.Secondary);

  private final String path;
  private final AudioInput audioInput;

  Sound(String name, AudioInput audioInput) {
    path = "/sounds/" + name + ".wav";
    this.audioInput = audioInput;
  }

  /**
   * Get the path of the sound resource.
   *
   * @return path.
   */
  public String getPath() {
    return path;
  }

  /**
   * Get the default audio input on which the sound is fed to the audioplayer.
   *
   * @return default audio input index.
   */
  public AudioInput getAudioInput() {
    return audioInput;
  }
}
