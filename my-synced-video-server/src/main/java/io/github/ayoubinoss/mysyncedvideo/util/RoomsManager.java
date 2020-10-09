package io.github.ayoubinoss.mysyncedvideo.util;

import io.github.ayoubinoss.mysyncedvideo.model.Room;
import io.github.ayoubinoss.mysyncedvideo.model.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Utility class to manage rooms
 *
 * @author ayoubinoss
 */
public class RoomsManager {

  /**
   * Map to store rooms with their users.
   */
  private Map<Room, LinkedList<User>> rooms;

  /**
   * Default constructor.
   */
  public RoomsManager() {
    rooms = new HashMap<Room, LinkedList<User>>();
  }

  /**
   * @return the map of rooms
   */
  public Map<Room, LinkedList<User>> getRooms() {
    return rooms;
  }

  /**
   * @param rooms rooms to set.
   */
  public void setRooms(
      Map<Room, LinkedList<User>> rooms) {
    this.rooms = rooms;
  }

  /**
   * Create new room.
   * @param room room to create
   * @param user user who create the room
   */
  public void addRoom(Room room, User user) {
    if(!isRoomExist(room)) {
      rooms.put(room, new LinkedList<User>());
      addUser(room, user);
    }
  }

  /**
   * Add new user to an existed room.
   * @param room room to join
   * @param user user to add
   */
  public void addUser(Room room, User user) {
    rooms.get(room).addLast(user);
  }

  /**
   * Check if a room is already created.
   * @param room room to check
   * @return either the room exist or not
   */
  public boolean isRoomExist(Room room) {
    return rooms.containsKey(room);
  }

  /**
   * Check if the uniqueness of the user handle within a room.
   * @param room room to join
   * @param user user to check
   * @return either the user handle is valid or not
   */
  public boolean isUserNameValid(Room room, User user) {
    if(!rooms.containsKey(room))
      rooms.put(room, new LinkedList<>());
    for (User u : rooms.get(room))
      if (u.getName().equals(user.getName()))
        return false;
    return true;
  }
}
