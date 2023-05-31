package com.blackout.util.audio;

/**
 * The audio input on which the sound is fed to the audioplayer.
 */
public enum AudioInput {
  Primary(0),
  Secondary(1),
  Tertiary(2);

  private static final int count = values().length;
  private final int index;

  AudioInput(int index) {
    this.index = index;
  }

  /**
   * Get the count of available audioInputs.
   *
   * @return count.
   */
  public static int getCount() {
    return count;
  }

  /**
   * Get the index of the audioInput.
   *
   * @return index.
   */
  public int getIndex() {
    return index;
  }
}
