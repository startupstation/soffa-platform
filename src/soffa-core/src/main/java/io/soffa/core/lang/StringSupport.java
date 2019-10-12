package io.soffa.core.lang;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StringSupport {

    private StringSupport() {
    }

    public static String toString(InputStream stream) throws IOException {
        return IOUtils.toString(stream, StandardCharsets.UTF_8);
    }

    public static boolean isNullOrEmpty(String value) {
        return StringUtils.isBlank(value);
    }

    public static String base32Encode(String value) {
        Base32 base32 = new Base32();
        return base32.encodeAsString(value.getBytes());
    }

    public static String url(String base, String uri) {
        return base.replaceAll("/$", "") + "/" + uri.replaceAll("^/", "");
    }

}
