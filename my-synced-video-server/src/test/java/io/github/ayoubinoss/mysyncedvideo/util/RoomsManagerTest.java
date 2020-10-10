package io.github.ayoubinoss.mysyncedvideo.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.github.ayoubinoss.mysyncedvideo.MySyncedVideoException;
import io.github.ayoubinoss.mysyncedvideo.model.Room;
import io.github.ayoubinoss.mysyncedvideo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RoomsManagerTest {

  RoomsManager roomsManager;

  @Before
  public void setUp() {
    roomsManager = new RoomsManager();
  }

  @Test
  public void shouldAddRoomWhenAddRoomIsMade() throws MySyncedVideoException{
    roomsManager.addRoom(new Room("newRoom"), new User());
    assertTrue(roomsManager.getRooms().containsKey(new Room("newRoom")));
  }

  @Test
  public void shouldAddUserWhenAddUserIsMade() throws MySyncedVideoException {
    User user = new User();
    user.setName("newUser");
    Room newRoom = new Room("newRoom");
    roomsManager.addRoom(newRoom, new User());
    roomsManager.addUser(newRoom, user);
    assertEquals("newUser", roomsManager.getRooms().get(newRoom).get(1).getName());
  }

  @Test
  public void shouldReturnTrueWhenUserHasValidName() throws MySyncedVideoException {
    User user = new User(null, "newUser");
    Room room = new Room("newRoom");
    roomsManager.addRoom(room, new User(null, "owner"));
    assertTrue(roomsManager.isUserNameValid(room, user));
  }

  @Test
  public void shouldReturnFalseWhenUserDoesntHaveValidName() throws MySyncedVideoException {
    User user = new User(null, "owner");
    Room room = new Room("newRoom");
    roomsManager.addRoom(room, new User(null, "owner"));
    assertFalse(roomsManager.isUserNameValid(room, user));
  }

  @Test
  public void shouldReturnTrueWhenRoomExist() throws MySyncedVideoException {
    Room room = new Room("newRoom");
    roomsManager.addRoom(room, new User());
    assertTrue(roomsManager.isRoomExist(room));
  }

  @Test
  public void shouldReturnFalseWhenRoomDoesNotExist() {
    Room newRoom = new Room("newRoom");
    assertFalse(roomsManager.isRoomExist(newRoom));
  }
}