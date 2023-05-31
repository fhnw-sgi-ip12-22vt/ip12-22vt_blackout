package com.blackout.util.audio;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class AudioPlayer implements Runnable {
  public static final int AUDIO_INPUT_SIZE = 3;
  private final AudioProcessor audioProcessor = new AudioProcessor();
  private final AudioInputStream[] clips = new AudioInputStream[AUDIO_INPUT_SIZE];
  private final AudioOutput output;
  private boolean running;

  public AudioPlayer(String headsetDevice, int headsetDeviceIndex,
                     String speakerDevice, int speakerDeviceIndex) {
    output = new AudioOutput(headsetDevice, headsetDeviceIndex, speakerDevice, speakerDeviceIndex);
  }

  public void playClip(String name, AudioInput audioInput) {
    stopClip(audioInput);
    try {
      InputStream stream = getClass().getResourceAsStream(name);
      Objects.requireNonNull(stream);
      BufferedInputStream bufferedStream = new BufferedInputStream(stream);
      clips[audioInput.getIndex()] = AudioSystem.getAudioInputStream(bufferedStream);
    } catch (Exception e) {
      clips[audioInput.getIndex()] = null;
    }
  }

  public void stopClip(AudioInput audioInput) {
    try {
      if (clips[audioInput.getIndex()] != null) {
        clips[audioInput.getIndex()].close();
        clips[audioInput.getIndex()] = null;
      }
    } catch (Exception e) {
      clips[audioInput.getIndex()] = null;
    }
  }

  public void startPlayer() {
    running = true;
    new Thread(this).start();
  }

  public void setHeadsetActive() {
    output.setHeadsetActive();
  }

  public void setSpeakerActive() {
    output.setSpeakerActive();
  }

  public void stopPlayer() {
    running = false;
  }

  @Override
  public void run() {
    while (running) {
      audioProcessor.process(output, clips);
    }
    output.stop();
  }
}
