package io.soffa.platform.core.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import io.soffa.platform.core.exception.TechnicalException;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Object>, String> {

    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) return null;
        return JSON.serialize(attribute).orElse(null);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) return Collections.emptyMap();
        try {
            MapLikeType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
            return mapper.readValue(dbData, type);
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }


}
