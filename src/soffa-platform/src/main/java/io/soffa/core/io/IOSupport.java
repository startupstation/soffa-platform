package io.soffa.core.io;

import io.soffa.core.exception.TechnicalException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IOSupport {

    private IOSupport() {
    }

    public static String getContent(InputStream stream) {
        try {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }

}
