package io.soffa.platform.core.commons;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

public class ObjectUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new KotlinModule())
        .configure(MapperFeature.USE_ANNOTATIONS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T convert(Object source, Class<T> target) {
        return mapper.convertValue(source, target);
    }
}
