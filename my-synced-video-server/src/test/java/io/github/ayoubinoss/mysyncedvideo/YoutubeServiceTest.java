package io.github.ayoubinoss.mysyncedvideo;

import static io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING;
import static io.grpc.MethodDescriptor.MethodType.UNARY;
import static org.junit.Assert.assertEquals;

import io.github.ayoubinoss.mysyncedvideo.services.YoutubeServiceGrpc;
import io.grpc.MethodDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*Test to verify that the proto file youtube_service.proto generate the expected service. */
@RunWith(JUnit4.class)
public class YoutubeServiceTest {

  @Test
  public void testDescriptor() {
    assertEquals("services.YoutubeService"
    , YoutubeServiceGrpc.getServiceDescriptor().getName());
  }

  @Test
  public void serviceMethodDescriptors() {
    MethodDescriptor<?, ?> genericType;

    genericType = YoutubeServiceGrpc.getStreamMethod();
    assertEquals(BIDI_STREAMING, genericType.getType());

    genericType = YoutubeServiceGrpc.getLoginMethod();
    assertEquals(UNARY, genericType.getType());
  }
}