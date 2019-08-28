package io.soffa.comons.core.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import io.soffa.comons.core.Collections;
import io.soffa.comons.core.exception.TechnicalException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JSON {

    public static final String ERR_JSON_PARSING = "ERR_JSON_PARSING";

    private JSON() {
    }

    private static ObjectMapper mapper = new ObjectMapper();

    public static Optional<String> serialize(Object input) {
        if (input == null) return Optional.empty();
        try {
            return Optional.of(mapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new TechnicalException(ERR_JSON_PARSING, e);
        }
    }

    public static <T> T deserialize(String input, Class<T> type) {
        try {
            return mapper.readValue(input, type);
        } catch (IOException e) {
            throw new TechnicalException(ERR_JSON_PARSING, e);
        }
    }

    public static <T> List<T> deserializeArray(InputStream input, Class<T> elementType) {
        try {
            ArrayType type = mapper.getTypeFactory().constructArrayType(elementType);
            return Arrays.asList(mapper.readValue(input, type));
        } catch (IOException e) {
            throw new TechnicalException(ERR_JSON_PARSING, e);
        }
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object input) {
        if (input instanceof Map) {
            return Collections.removeNullValues((Map<String, Object>) input);
        }
        MapLikeType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
        return Collections.removeNullValues(mapper.convertValue(input, type));
    }


}
