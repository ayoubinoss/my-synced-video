package io.github.ayoubinoss.mysyncedvideo.model;

import io.github.ayoubinoss.mysyncedvideo.services.StreamReq;
import io.grpc.stub.StreamObserver;
import java.util.LinkedList;

public class User {
  private StreamObserver<StreamReq> observer;
  private String name;

  public User() {
    this.name = "jhon doe";
    this.observer = null;
  }

  public User(
      StreamObserver<StreamReq> observer, String name) {
    this.observer = observer;
    this.name = name;
  }

  public StreamObserver<StreamReq> getObserver() {
    return observer;
  }

  public void setObserver(
      StreamObserver<StreamReq> observer) {
    this.observer = observer;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
