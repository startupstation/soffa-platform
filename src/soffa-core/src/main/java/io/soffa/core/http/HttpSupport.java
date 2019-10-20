package io.soffa.core.http;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class HttpSupport {

    public static boolean isBasicAuthenticate(String header) {
        if (StringUtils.isBlank(header)) return false;
        return header.toLowerCase().startsWith("basic ");
    }

    public static Optional<String> getBasicAuthCredentials(String header) {
        if (StringUtils.isBlank(header)) return Optional.empty();
        return Optional.of(header.toLowerCase().substring("basic ".length()));
    }

}
