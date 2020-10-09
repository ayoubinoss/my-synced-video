package io.github.ayoubinoss.mysyncedvideo.model;

import java.util.Objects;

public class Room {

  private String roomName;

  public Room(String roomName) {
    this.roomName = roomName;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomId(String roomName) {
    this.roomName = roomName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Room room = (Room) o;
    return Objects.equals(roomName, room.roomName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomName);
  }
}
