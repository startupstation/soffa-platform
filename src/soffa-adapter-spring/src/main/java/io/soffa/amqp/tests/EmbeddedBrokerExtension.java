package io.soffa.amqp.tests;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EmbeddedBrokerExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        EmbeddedMQ.boostrap();
        Thread.sleep(100);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        EmbeddedMQ.shutdown();

    }
}
