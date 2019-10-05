package io.soffa.amqp.tests;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EmbeddedBrokerExtension implements BeforeAllCallback, AfterAllCallback {

    public static String DEFAULT_QUEUE_NAME = "default";

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        EmbeddedMQ.boostrap(DEFAULT_QUEUE_NAME);
        Thread.sleep(100);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        EmbeddedMQ.shutdown();

    }
}
