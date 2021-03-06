package com.netifi.quickstart.client;

import com.netifi.broker.BrokerClient;
import com.netifi.broker.rsocket.BrokerSocket;
import com.netifi.common.tags.Tags;
import com.netifi.quickstart.service.protobuf.HelloRequest;
import com.netifi.quickstart.service.protobuf.HelloServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Starts the Netifi Quickstart Client */
public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String... args) {

    // Build Netifi Broker Connection
    BrokerClient netifi =
            BrokerClient.tcp()
            .group("quickstart.clients") // Group name of client
            .destination("client1") // Name of this client instance
            .accessKey(9007199254740991L)
            .accessToken("kTBDVtfRBO4tHOnZzSyY5ym2kfY=")
            .host("localhost") // Netifi Broker Host
            .port(8001) // Netifi Broker Port
            .disableSsl() // Disabled for parity with Javascript Tutorial
            .build();

    // Connect to Netifi Platform
    BrokerSocket conn =
        netifi.groupServiceSocket("quickstart.services.helloservices", Tags.empty());

    // Create Client to Communicate with the HelloService (included example service)
    HelloServiceClient client = new HelloServiceClient(conn);

    // Create Request to HelloService
    HelloRequest request = HelloRequest.newBuilder().setName("World").build();

    logger.info("Sending 'World' to HelloService...");

    // Call the HelloService
    logger.info(client.sayHello(request).block());
  }
}
