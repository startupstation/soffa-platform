package io.soffa.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Xml {


    private Xml() {
    }

    private static ObjectMapper mapper = new XmlMapper().registerModule(new KotlinModule());

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String input) {
        try {
            return mapper.readValue((String) input, Map.class);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }


}
