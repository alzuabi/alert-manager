package com.alertmanager.demo.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private static final ObjectMapper json = new ObjectMapper().findAndRegisterModules();


    @Override
    public String convertToDatabaseColumn(Map<String, Object> data) {
        if (null == data) {
            // You may return null if you prefer that style
            return "{}";
        }

        try {
            return json.writeValueAsString(data);

        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        if (null == s) {
            // You may return null if you prefer that style
            return new HashMap<>();
        }

        try {
            return json.readValue(s, new TypeReference<>() {
            });

        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to map", e);
        }
    }
}
