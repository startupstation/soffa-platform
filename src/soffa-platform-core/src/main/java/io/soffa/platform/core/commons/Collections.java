package io.soffa.platform.core.commons;

import java.util.Map;
import java.util.stream.Collectors;

public class Collections {

    public static Map<String, Object> removeNullValues(Map<String, Object> input) {
        if (input == null) {
            return null;
        }
        return input.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
