package com.blackout.util.audio;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.sound.sampled.AudioSystem;
import org.junit.jupiter.api.Test;

public class AudioProcessorTest {
  private static byte[] getBytesFromAudioFile(String filename) throws Exception {
    InputStream inputStream = AudioProcessorTest.class.getResourceAsStream(filename);
    Objects.requireNonNull(inputStream);
    return AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream)).readAllBytes();
  }

  @Test
  public void checkIfAudioConversionWorks() throws Exception {
    // Given
    var processor = new AudioProcessor();
    byte[] in = getBytesFromAudioFile("test_float.wav");
    byte[] expected = new BufferedInputStream(getClass().getResourceAsStream("expected"))
        .readAllBytes();
    float[] out = new float[in.length / 4];
    byte[] actual = new byte[in.length / 2];

    // When
    processor.convertTo32BitFloatArray(in, out, out.length);
    processor.convertTo16BitSignedPcmArray(out, actual, out.length);
    // Then
    assertArrayEquals(expected, actual);
  }

  @Test
  public void checkIfMixingWorks() {
    // Given
    var processor = new AudioProcessor();
    float[] a = new float[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    float[] b = new float[] {.6f, .6f, .6f, .6f, .6f, .6f, 0, 0, 6f, 6f};
    float[] expect = new float[] {.2f, .2f, .2f, .2f, .2f, .2f, 0, 0, 1f, 1f};

    // When
    processor.mixTwoFloatArrays(b, a, b.length);

    // Then
    assertArrayEquals(expect, a);
  }
}