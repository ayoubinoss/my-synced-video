package io.github.ayoubinoss.mysyncedvideo;

import static org.junit.Assert.assertEquals;

import io.github.ayoubinoss.mysyncedvideo.services.LoginReq;
import io.github.ayoubinoss.mysyncedvideo.services.LoginRes;
import io.github.ayoubinoss.mysyncedvideo.services.YoutubeServiceGrpc;
import io.github.ayoubinoss.mysyncedvideo.services.YoutubeServiceImpl;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GrpcServerTest {

  public final GrpcCleanupRule cleanupRule = new GrpcCleanupRule();

  @Test
  public void YoutubeServiceImpl_loginReply() throws Exception {

    //Generate a unique in-process server name
    String serverName = InProcessServerBuilder.generateName();

    //create a server, add service, start and register for automatic graceful shutdown.
    cleanupRule.register(InProcessServerBuilder.forName(serverName).directExecutor()
        .addService(new YoutubeServiceImpl()).build().start());

    YoutubeServiceGrpc.YoutubeServiceBlockingStub blockingStub = YoutubeServiceGrpc.newBlockingStub(
        //create a client channel and register for automatic graceful shutdown.
        cleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
    );

    LoginRes reply = blockingStub.login(LoginReq
        .newBuilder()
        .setRoomId("newRoom")
        .setUserName("newUser").build());

    assertEquals("success", reply.getToken());
  }
}