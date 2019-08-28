package io.soffa.core.data.converters;


import io.soffa.comons.core.databind.JSON;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) return null;
        return JSON.serialize(attribute).orElse(null);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return JSON.toMap(dbData);
    }


}
