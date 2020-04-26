package io.soffa.platform.core.commons;

import com.fasterxml.uuid.Generators;
import org.apache.commons.lang3.RandomStringUtils;
import org.hashids.Hashids;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public  class IDs {

    private IDs() {
    }

    public static String createId() {
        return createId("");
    }

    public static String createId(String prefix) {
        return prefix + Generators.randomBasedGenerator().generate().toString().replace("-", "");
    }

    public static String shortId(){
        return shortId("", "");
    }

    public static String shortId(String value){
        return shortId(value, "");
    }

    public static String shortId(String prefix, String salt){
        return prefix + hashId(salt);
    }

    private static String generateString() {
        return generateString(true);
    }

    private static String generateString(boolean keepDash) {
        String uuid = generate().toString();
        if (!keepDash) return uuid.replace("-", "");
        return uuid;
    }

    private static String hashId(String salt) {
        long value = System.currentTimeMillis();
        return RandomStringUtils.randomAlphabetic(1) + new Hashids(salt + generateString()).encode(value);
    }

    private static UUID generate() {
        return Generators.randomBasedGenerator().generate();
    }

}
