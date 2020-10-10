package io.github.ayoubinoss.mysyncedvideo.util;

import io.github.ayoubinoss.mysyncedvideo.MySyncedVideoException;
import io.github.ayoubinoss.mysyncedvideo.model.Room;
import io.github.ayoubinoss.mysyncedvideo.model.User;
import io.github.ayoubinoss.mysyncedvideo.services.LoginRes;
import io.grpc.stub.StreamObserver;
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
  public void addRoom(Room room, User user) throws MySyncedVideoException {
    if(!isRoomExist(room)) {
      rooms.put(room, new LinkedList<User>());
      addUser(room, user);
    }
    else {
      throw new MySyncedVideoException("the room name is already in use. pick another one");
    }
  }

  /**
   * Add new user to an existed room.
   * @param room room to join
   * @param user user to add
   */
  public void addUser(Room room, User user) throws MySyncedVideoException {
    if(isUserNameValid(room, user))
      rooms.get(room).addLast(user);
    else
      throw new MySyncedVideoException("This handle is taken by another user, choose another one");
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
    if(rooms.get(room) != null)
      for (User u : rooms.get(room))
        if (u.getName().equals(user.getName()))
          return false;
    return true;
  }

  /**
   * login function
   * @param room the room to create/join
   * @param user the user
   * @throws MySyncedVideoException exception
   */
  public void login(Room room, User user, StreamObserver<LoginRes> responseObserver)
      throws MySyncedVideoException {
    if(!isRoomExist(room))
      addRoom(room, user);
    else
      addUser(room, user);
    responseObserver.onNext(LoginRes.newBuilder().setToken("success").build());
    responseObserver.onCompleted();
  }
}
