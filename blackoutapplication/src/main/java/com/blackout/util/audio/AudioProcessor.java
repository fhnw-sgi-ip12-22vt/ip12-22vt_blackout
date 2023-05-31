package com.blackout.util.audio;

import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;

public class AudioProcessor {
  private static final int BYTES_PER_FLOAT = 4;
  private static final int BYTES_PER_SHORT = 2;
  private static final int INPUT_BUFFER_SIZE = 4096;
  private static final int PROCESSING_BUFFER_SIZE = INPUT_BUFFER_SIZE / BYTES_PER_FLOAT;
  private static final int OUTPUT_BUFFER_SIZE = INPUT_BUFFER_SIZE / BYTES_PER_SHORT;
  private final byte[] inputBuffer = new byte[INPUT_BUFFER_SIZE];
  private final float[] dataBuffer = new float[PROCESSING_BUFFER_SIZE];
  private final float[] mixBuffer = new float[PROCESSING_BUFFER_SIZE];
  private final byte[] outputBuffer = new byte[OUTPUT_BUFFER_SIZE];
  int consumedInputBuffer;
  int maxConsumedInputBuffer;
  int consumedProcessingBuffer;
  int maxConsumedProcessingBuffer;
  int consumedOutputBuffer;

  public void mixTwoFloatArrays(float[] in, float[] out, int range) {
    for (int i = 0; i < range; i++) {
      out[i] = normalize(out[i] + (in[i] / AudioPlayer.AUDIO_INPUT_SIZE));
    }
  }

  public void convertTo32BitFloatArray(byte[] in, float[] out, int range) {
    // from https://stackoverflow.com/a/4513578
    int binaryFloat;
    for (int i = 0; i < range; i++) {
      binaryFloat = (in[i * BYTES_PER_FLOAT] & 0xFF)
          | ((in[i * BYTES_PER_FLOAT + 1] & 0xFF) << 8)
          | ((in[i * BYTES_PER_FLOAT + 2] & 0xFF) << 16)
          | ((in[i * BYTES_PER_FLOAT + 3] & 0xFF) << 24);
      out[i] = Float.intBitsToFloat(binaryFloat);
    }
  }

  public void convertTo16BitSignedPcmArray(float[] in, byte[] out, int range) {
    // From https://stackoverflow.com/a/60198512
    int mapped;
    for (int i = 0; i < range; i++) {
      mapped = (int) (in[i] * 32768);
      out[i * BYTES_PER_SHORT] = (byte) (mapped & 0xFF);
      out[i * BYTES_PER_SHORT + 1] = (byte) ((mapped >> 8) & 0xFF);
    }
  }

  private static float normalize(float value) {
    return Math.max(Math.min(value, 1.0f), -1.0f);
  }

  public void process(AudioOutput output, AudioInputStream[] clips) {
    try {
      if ((maxConsumedInputBuffer = mixAudioStreams(clips)) == -1) {
        return;
      }
      convertMix();
      writeToOutput(output);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private int mixAudioStreams(AudioInputStream[] clips) throws IOException {
    maxConsumedInputBuffer = -1;
    Arrays.fill(mixBuffer, 0);
    for (AudioInputStream clip : clips) {
      if (clip == null || (consumedInputBuffer = clip.read(inputBuffer)) == -1) {
        continue;
      }
      maxConsumedInputBuffer = Math.max(consumedInputBuffer, maxConsumedInputBuffer);
      consumedProcessingBuffer = consumedInputBuffer / BYTES_PER_FLOAT;
      convertTo32BitFloatArray(inputBuffer, dataBuffer, consumedProcessingBuffer);
      mixTwoFloatArrays(dataBuffer, mixBuffer, consumedProcessingBuffer);
    }
    return maxConsumedInputBuffer;
  }

  private void convertMix() {
    maxConsumedProcessingBuffer = maxConsumedInputBuffer / BYTES_PER_FLOAT;
    convertTo16BitSignedPcmArray(mixBuffer, outputBuffer, maxConsumedProcessingBuffer);
  }

  private void writeToOutput(AudioOutput output) {
    consumedOutputBuffer = maxConsumedInputBuffer / BYTES_PER_SHORT;
    output.write(outputBuffer, consumedOutputBuffer);
  }
}
