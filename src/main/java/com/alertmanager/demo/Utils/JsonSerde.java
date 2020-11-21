package com.alertmanager.demo.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;


public class JsonSerde<T> implements Serde<T> {

    public static ObjectMapper json = new ObjectMapper().findAndRegisterModules();

    private final Class<T> clazz;

    public JsonSerde(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Serializer<T> serializer()  {
        return (s, t) -> {
            if (t == null)
                return null;
            try {
                return json.writeValueAsBytes(t);
            } catch (JsonProcessingException e) {
                return null;
            }
        };
    }

    @Override
    public Deserializer<T> deserializer() {
        return (s, bytes) -> {
            if (bytes == null) {
                return null;
            }
            try {
                return json.readValue(bytes, clazz);
            } catch (IOException e) {
                return null;
            }
        };
    }


    public static <T> JsonSerde<T> jsonSerde(Class<T> tClass) {
        return new JsonSerde<>(tClass);
    }
}
