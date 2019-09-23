package io.soffa.core.converters;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.soffa.core.JSON;
import io.soffa.core.exception.TechnicalException;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) return null;
        return JSON.serialize(attribute).orElse(null);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) return Collections.emptyMap();
        try {
            //noinspection unchecked
            return mapper.readValue(dbData, Map.class);
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }


}
