package com.blackout.util.audio;

import com.blackout.util.dialog.WarningsCollector;
import java.util.Arrays;
import java.util.Optional;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

public class AudioOutput {
  private static final AudioFormat FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
      44_100f, 16, 2, 4, 44_100f, false);
  private static final DataLine.Info DATA_LINE_INFO =
      new DataLine.Info(SourceDataLine.class, FORMAT);
  private final Object lock = new Object();
  private final String headsetDevice;
  private final int headsetDeviceIndex;
  private final String speakerDevice;
  private final int speakerDeviceIndex;
  private SourceDataLine currentLine;

  public AudioOutput(String headsetDevice, int headsetDeviceIndex,
                     String speakerDevice, int speakerDeviceIndex) {
    this.headsetDevice = headsetDevice;
    this.headsetDeviceIndex = headsetDeviceIndex;
    this.speakerDevice = speakerDevice;
    this.speakerDeviceIndex = speakerDeviceIndex;
    setSpeakerActive();
  }

  private static SourceDataLine getSourceDataLine(String mixerDescription, int mixerIndex) {
    var mixer = getMixer(mixerDescription, mixerIndex);
    if (mixer.isEmpty()) {
      printError(
          "Mixer: " + mixerDescription + " is non-existent! Please configure a valid Mixer.");
      return null;
    }

    return getOutputLine(mixer.get(), false).orElse(null);
  }

  private static void printError(String message) {
    System.out.println("\u001B[31m" + message + "\u001B[0m");
    WarningsCollector.add(message);
  }

  public static String[] getAvailableMixers() {
    return Arrays.stream(AudioSystem.getMixerInfo())
        .filter(AudioOutput::isOutputLineSupported)
        .map(Mixer.Info::getDescription)
        .toArray(String[]::new);
  }

  private static Optional<Mixer.Info> getMixer(String mixerDescription, int mixerIndex) {
    Mixer.Info[] mixerInfos = Arrays.stream(AudioSystem.getMixerInfo())
        .filter(m -> m.getDescription().contains(mixerDescription))
        .toArray(Mixer.Info[]::new);
    boolean mixerInArray = mixerInfos.length > 0 && mixerIndex < mixerInfos.length;
    if (!mixerInArray) {
      return Optional.empty();
    }

    return Optional.of(mixerInfos[mixerIndex]);
  }

  private static boolean isOutputLineSupported(Mixer.Info mixerInfo) {
    return getOutputLine(mixerInfo, true).isPresent();
  }

  private static Optional<SourceDataLine> getOutputLine(Mixer.Info mixerInfo, boolean supressErrors) {
    Mixer mixer;
    try {
      mixer = AudioSystem.getMixer(mixerInfo);
    } catch (Exception e) {
      if (!supressErrors) {
        printError("Mixer: " + mixerInfo + " is not available: " + e.getMessage());
      }
      return Optional.empty();
    }

    if (!AudioSystem.isLineSupported(DATA_LINE_INFO)) {
      if (!supressErrors) {
        printError("Mixer: " + mixerInfo + " does not support the format: " + FORMAT);
      }
      return Optional.empty();
    }

    Line.Info[] lineInfos = mixer.getSourceLineInfo(DATA_LINE_INFO);
    if (lineInfos.length < 1) {
      if (!supressErrors) {
        printError("Mixer: " + mixerInfo + " does not have a output line.");
      }
      return Optional.empty();
    }

    Line.Info lineInfo = lineInfos[0];
    try {
      return Optional.of((SourceDataLine) mixer.getLine(lineInfo));
    } catch (Exception e) {
      if (!supressErrors) {
        printError("Mixer: " + mixerInfo + " can not instantiate the output: " + lineInfo);
      }
      return Optional.empty();
    }
  }

  public void setHeadsetActive() {
    setDevice(getSourceDataLine(headsetDevice, headsetDeviceIndex));
  }

  public void setSpeakerActive() {
    setDevice(getSourceDataLine(speakerDevice, speakerDeviceIndex));
  }

  public void stop() {
    synchronized (lock) {
      stopLine();
    }
  }

  private void setDevice(SourceDataLine line) {
    synchronized (lock) {
      stopLine();
      currentLine = line;
      startLine();
    }
  }

  private void startLine() {
    if (currentLine == null) {
      return;
    }
    try {
      currentLine.open(FORMAT);
      currentLine.start();
    } catch (Exception e) {
      printError(e.toString());
    }
  }

  private void stopLine() {
    if (currentLine == null) {
      return;
    }
    currentLine.flush();
    currentLine.close();
  }

  public void write(byte[] outputBuffer, int consumed) {
    synchronized (lock) {
      if (currentLine == null || consumed < 0) {
        return;
      }
      currentLine.write(outputBuffer, 0, consumed);
    }
  }
}
