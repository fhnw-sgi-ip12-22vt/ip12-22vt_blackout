package com.blackout.util.sound;

import com.blackout.util.audio.AudioConfig;
import com.blackout.util.audio.AudioInput;
import com.blackout.util.audio.AudioPlayer;
import com.blackout.util.dialog.WarningsCollector;
import java.util.Optional;

/**
 * SoundStage
 */
public class SoundStage {
  private final String name;
  private Optional<AudioPlayer> player;

  public SoundStage(String name) {
    this.name = name;
    this.player = Optional.empty();
  }

  /**
   * Initialize the player.
   */
  public void init(AudioConfig config) {
    close();
    player = Optional.of(new AudioPlayer(config.getHeadset(), config.getHeadsetIndex(),
        config.getSpeaker(), config.getSpeakerIndex()));
    player.ifPresent(AudioPlayer::startPlayer);
  }

  /**
   * Close the player.
   */
  public void close() {
    player.ifPresent(AudioPlayer::stopPlayer);
    player = Optional.empty();
  }

  /**
   * switch to the headset on player.
   */
  public void switchToHeadset() {
    player.ifPresentOrElse(AudioPlayer::setHeadsetActive, this::logNotInitError);
  }

  /**
   * switch to the speaker on player.
   */
  public void switchToSpeaker() {
    player.ifPresentOrElse(AudioPlayer::setSpeakerActive, this::logNotInitError);
  }

  /**
   * Plays a specific clip on player.
   *
   * @param sound the sound which should be played (using the sounds default audio input index).
   */
  public void play(Sound sound) {
    play(sound, sound.getAudioInput());
  }

  /**
   * Plays a specific clip on player.
   *
   * @param sound      the sound which should be played.
   * @param audioInput on which audio input the sound should be played.
   */
  public void play(Sound sound, AudioInput audioInput) {
    player.ifPresentOrElse(x -> x.playClip(sound.getPath(), audioInput), this::logNotInitError);
  }

  /**
   * Stops a specific clip on player.
   *
   * @param sound the sound which should be played (using the sounds default audio input index).
   */
  public void stop(Sound sound) {
    player.ifPresentOrElse(x -> x.stopClip(sound.getAudioInput()), this::logNotInitError);
  }

  /**
   * Stops a specific clip on player.
   *
   * @param audioInput on which audio input the sound should be stopped.
   */
  public void stop(AudioInput audioInput) {
    player.ifPresentOrElse(x -> x.stopClip(audioInput), this::logNotInitError);
  }

  /**
   * Stops all clips on player.
   */
  public void stopAll() {
    player.ifPresentOrElse(x -> {
      for (AudioInput value : AudioInput.values()) {
        x.stopClip(value);
      }
    }, this::logNotInitError);
  }

  private void logNotInitError() {
    String msg = "The player: " + name + " is not initialized!";
    System.out.println(msg);
    WarningsCollector.add(msg);
  }
}
