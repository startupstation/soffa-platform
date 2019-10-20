package io.soffa.core.tests;

import org.apache.qpid.server.SystemLauncher;
import org.apache.qpid.server.model.ConfiguredObject;
import org.apache.qpid.server.model.SystemConfig;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class EmbeddedMQ {

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static final String DEFAULT_INITIAL_CONFIGURATION_LOCATION = "qpid-embedded-inmemory-configuration.json";
    private static SystemLauncher systemLauncher;
    private static boolean boostraped = false;
    private static int consumers = 0;

    private EmbeddedMQ() {
    }

    public static void boostrap() throws Exception {
        consumers++;
        if (!boostraped) {
            Map<String, Object> attributes = new HashMap<>();
            URL initialConfigUrl = EmbeddedMQ.class.getClassLoader().getResource(DEFAULT_INITIAL_CONFIGURATION_LOCATION);
            attributes.put(ConfiguredObject.TYPE, "Memory");
            assert initialConfigUrl != null;
            attributes.put(SystemConfig.INITIAL_CONFIGURATION_LOCATION, initialConfigUrl.toExternalForm());
            attributes.put(SystemConfig.STARTUP_LOGGED_TO_SYSTEM_OUT, true);
            systemLauncher = new SystemLauncher();
            systemLauncher.startup(attributes);
            boostraped = true;
        }
    }

    public static void shutdown() {
        consumers--;
        executorService.schedule(() -> {
            if (consumers == 0) {
                systemLauncher.shutdown();
            }
        }, 2, TimeUnit.SECONDS);

    }
}
