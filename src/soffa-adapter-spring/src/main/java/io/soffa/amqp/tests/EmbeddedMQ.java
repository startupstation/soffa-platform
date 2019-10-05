package io.soffa.amqp.tests;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.qpid.server.SystemLauncher;
import org.apache.qpid.server.model.SystemConfig;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.apache.qpid.server.model.ConfiguredObject.TYPE;

@SuppressWarnings("ALL")
public class EmbeddedMQ {

    private static final String DEFAULT_INITIAL_CONFIGURATION_LOCATION = "qpid-embedded-inmemory-configuration.json";
    private static SystemLauncher systemLauncher;

    private EmbeddedMQ() {
    }

    public static void boostrap(String defaultQueue) throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        URL initialConfigUrl = EmbeddedMQ.class.getClassLoader().getResource(DEFAULT_INITIAL_CONFIGURATION_LOCATION);
        attributes.put(TYPE, "Memory");
        assert initialConfigUrl != null;
        attributes.put(SystemConfig.INITIAL_CONFIGURATION_LOCATION, initialConfigUrl.toExternalForm());
        attributes.put(SystemConfig.STARTUP_LOGGED_TO_SYSTEM_OUT, true);
        systemLauncher = new SystemLauncher();
        systemLauncher.startup(attributes);

        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        try(Connection connection = factory.newConnection()) {
           try(Channel channel = connection.createChannel()) {
               channel.queueDeclare(defaultQueue, true, false, false, new HashMap<>());
           }
        }

    }

    public static void shutdown() {
        systemLauncher.shutdown();
    }
}
