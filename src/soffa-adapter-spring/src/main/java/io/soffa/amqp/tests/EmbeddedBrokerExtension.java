package io.soffa.amqp.tests;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EmbeddedBrokerExtension implements BeforeAllCallback, AfterAllCallback {

    public static String DEFAULT_QUEUE_NAME = "default";
    private static final boolean disabled = StringUtils.trimToEmpty(System.getenv("EMBEDDED_TEST_BROKER")).equals("false");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (disabled) {
            return;
        }
        EmbeddedMQ.boostrap(DEFAULT_QUEUE_NAME);
        Thread.sleep(100);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (disabled) return;
        EmbeddedMQ.shutdown();
    }
}
