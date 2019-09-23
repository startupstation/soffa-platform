package io.soffa.amqp.tests;

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

    public static void boostrap() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        URL initialConfigUrl = EmbeddedMQ.class.getClassLoader().getResource(DEFAULT_INITIAL_CONFIGURATION_LOCATION);
        attributes.put(TYPE, "Memory");
        assert initialConfigUrl != null;
        attributes.put(SystemConfig.INITIAL_CONFIGURATION_LOCATION, initialConfigUrl.toExternalForm());
        attributes.put(SystemConfig.STARTUP_LOGGED_TO_SYSTEM_OUT, true);
        systemLauncher = new SystemLauncher();
        systemLauncher.startup(attributes);
    }

    public static void shutdown() {
        systemLauncher.shutdown();
    }
}
