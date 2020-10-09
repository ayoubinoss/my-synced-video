package io.github.ayoubinoss.mysyncedvideo;

import com.google.grpcweb.GrpcPortNumRelay;
import com.google.grpcweb.JettyWebserverForGrpcwebTraffic;
import io.github.ayoubinoss.mysyncedvideo.services.YoutubeServiceImpl;
import io.github.ayoubinoss.mysyncedvideo.util.Util;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a gRPC server.
 */
public class GrpcServer {

  private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

  private Server server;

  private void start() throws IOException {

    /* Start the grpc server on grpc port */
    int grpcPort = Util.getGrpcServicePortNum();
    server = ServerBuilder.forPort(grpcPort)
        .addService((BindableService) new YoutubeServiceImpl())
        .build()
        .start();
    logger.info("Server started, listening on " + grpcPort);

    /* start grpc-web proxy on grpc-web-port*/
    int grpcWebPort = Util.getGrpcwebServicePortNum();

    (new JettyWebserverForGrpcwebTraffic(grpcWebPort)).start();

    // grpc-web proxy needs to know the grpc-port# so it could connect to the grpc service.
    GrpcPortNumRelay.setGrpcPortNum(grpcPort);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        try {
          GrpcServer.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final GrpcServer server = new GrpcServer();
    server.start();
    server.blockUntilShutdown();
  }
}