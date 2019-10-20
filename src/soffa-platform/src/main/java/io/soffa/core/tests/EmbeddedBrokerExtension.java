package io.soffa.core.tests;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EmbeddedBrokerExtension implements BeforeAllCallback, AfterAllCallback {

    private static final boolean DISABLED = StringUtils.trimToEmpty(System.getenv("EMBEDDED_TEST_BROKER")).equals("false");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (DISABLED) return;
        EmbeddedMQ.boostrap();
        Thread.sleep(1000);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (DISABLED) return;
        EmbeddedMQ.shutdown();
    }
}
