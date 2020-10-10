package io.github.ayoubinoss.mysyncedvideo.services;

import io.github.ayoubinoss.mysyncedvideo.MySyncedVideoException;
import io.github.ayoubinoss.mysyncedvideo.model.Room;
import io.github.ayoubinoss.mysyncedvideo.model.User;
import io.github.ayoubinoss.mysyncedvideo.services.YoutubeServiceGrpc.YoutubeServiceImplBase;
import io.github.ayoubinoss.mysyncedvideo.util.RoomsManager;
import io.grpc.stub.StreamObserver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YoutubeServiceImpl extends YoutubeServiceImplBase {

  final static Logger logger = Logger.getLogger(YoutubeServiceImpl.class.getName());

  private RoomsManager roomsManager;

  public YoutubeServiceImpl() {
    this.roomsManager = new RoomsManager();
  }

  @Override
  public void login(LoginReq request, StreamObserver<LoginRes> responseObserver) {
    Room room = new Room(request.getRoomId());
    User user = new User(null, request.getUserName());
    try {
      roomsManager.login(room, user, responseObserver);
    } catch(MySyncedVideoException e) {
      logger.log(Level.SEVERE, e.getMessage());
      responseObserver.onError(e);
    }
  }

  @Override
  public StreamObserver<StreamReq> stream(StreamObserver<StreamRes> responseObserver) {
    return super.stream(responseObserver);
  }
}
