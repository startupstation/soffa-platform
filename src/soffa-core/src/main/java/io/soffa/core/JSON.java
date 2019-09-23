package io.soffa.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import io.soffa.core.exception.TechnicalException;
import com.fasterxml.jackson.module.kotlin.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JSON {

    private static final String JSONERR = "JSONERR";
    private static final String ERR_JSON_PARSING = "ERR_JSON_PARSING";

    private JSON() {
    }

    private static ObjectMapper mapper = new ObjectMapper().registerModule(new KotlinModule());

    public static Optional<String> serialize(Object input) {
        if (input == null) return Optional.empty();
        try {
            return Optional.of(mapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new TechnicalException(ERR_JSON_PARSING, e);
        }
    }

    public static String serializeSafe(@NotNull Object input) {
        try {
            return mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new TechnicalException(JSONERR, e);
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
        if (input instanceof String) {
            try {
                return mapper.readValue((String) input, type);
            } catch (IOException e) {
                return new HashMap<>();
            }
        } else {
            return Collections.removeNullValues(mapper.convertValue(input, type));
        }
    }


}
