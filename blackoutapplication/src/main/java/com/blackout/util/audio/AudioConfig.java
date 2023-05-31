package com.blackout.util.audio;

import com.blackout.util.config.ConfigManager;

/**
 * SoundFx is used for playing sounds.
 */


public record AudioConfig(String headsetConfigProperty, String headsetIndexConfigProperty,
                          String speakerConfigProperty, String speakerIndexConfigProperty) {
  public String getHeadset() {
    return ConfigManager.getProperty(headsetConfigProperty);
  }

  public int getHeadsetIndex() {
    return Integer.parseInt(ConfigManager.getProperty(headsetIndexConfigProperty));
  }

  public String getSpeaker() {
    return ConfigManager.getProperty(speakerConfigProperty);
  }

  public int getSpeakerIndex() {
    return Integer.parseInt(ConfigManager.getProperty(speakerIndexConfigProperty));
  }

  public static AudioConfig getAudioConfigA() {
    return new AudioConfig("app.audio.headsetA", "app.audio.headsetAIndex",
        "app.audio.speakerA", "app.audio.speakerAIndex");
  }

  public static AudioConfig getAudioConfigB() {
    return new AudioConfig("app.audio.headsetB", "app.audio.headsetBIndex",
        "app.audio.speakerB", "app.audio.speakerBIndex");
  }
}
