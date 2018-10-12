package io.netifi.proteus.quickstart.client;

import io.netifi.proteus.Proteus;
import io.netifi.proteus.quickstart.service.protobuf.HelloRequest;
import io.netifi.proteus.quickstart.service.protobuf.HelloServiceClient;
import io.netifi.proteus.rsocket.ProteusSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Starts the Proteus Quickstart Client
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String... args) {

        // Build Netifi Proteus Connection
        Proteus netifi =
                Proteus.builder()
                        .group("quickstart.clients")                    // Group name of client
                        .destination("client1")                         // Name of this client instance
                        .accessKey(9007199254740991L)
                        .accessToken("kTBDVtfRBO4tHOnZzSyY5ym2kfY=")
                        .host("localhost")                              // Proteus Broker Host
                        .port(8001)                                     // Proteus Broker Port
                        .build();

        // Connect to Netifi Proteus Platform
        ProteusSocket conn = netifi.group("quickstart.services.helloservices");
        
        // Create Client to Communicate with the HelloService (included example service)
        HelloServiceClient client = new HelloServiceClient(conn);

        // Create Request to HelloService
        HelloRequest request = HelloRequest.newBuilder()
                .setName("World")
                .build();

        logger.info("Sending 'World' to HelloService...");

        // Call the HelloService
        logger.info(client.sayHello(request).block());
    }
}
