package com.blackout.util.rooms;

public class Room {
  private String roomId;
  private String roomTitle;
  private String roomDescription;

  public Room(String roomId, String roomTitle, String roomDescription) {
    this.roomId = roomId;
    this.roomTitle = roomTitle;
    this.roomDescription = roomDescription;
  }

  public String getRoomId() {
    return roomId;
  }

  public String getRoomTitle() {
    return roomTitle;
  }

  public String getRoomDescription() {
    return roomDescription;
  }

  @Override
  public String toString() {
    return roomTitle;
  }
}
