package com.alertmanager.demo.Utils;

import com.alertmanager.demo.Domin.Alert;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // nothing to do
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null)
            return null;

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }
//
//    @Override
//    public byte[] serialize(String topic, Headers headers, T data) {
//        return new byte[0];
//    }

    @Override
    public void close() {
        // nothing to do
    }

}

class JsonDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> tClass;

    public JsonDeserializer() {
    }

    public JsonDeserializer(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // nothing to do
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        T data;
        try {
            data = objectMapper.readValue(bytes, tClass);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {
        // nothing to do
    }
}
class GsonSerializer implements   Serializer<Alert> {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private final ObjectMapper objectMapper = new ObjectMapper();

    static private final Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String s, Alert person) {
        // Transform the Person object to String
        String line = gson.toJson(person);
//        if (person == null)
//            return null;
//        else {
//            try {
//                return objectMapper.writeValueAsBytes(person);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        return null;
        // Return the bytes from the String 'line'
        return line.getBytes(CHARSET);
    }
//        return new byte[0];
//    }

    @Override
    public void close() {

    }
}
class GsonDeserializer implements  Deserializer<Alert> {
    //    private final ObjectMapper objectMapper = new ObjectMapper();
//    private Class<Person> tClass;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    static private final Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public Alert deserialize(String topic, byte[] bytes) {
        try {
            // Transform the bytes to String
            String person = new String(bytes, CHARSET);
            // Return the Person object created from the String 'person'
            return gson.fromJson(person, Alert.class);
//            return objectMapper.readValue(bytes, Person.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading bytes", e);
        }
    }

    @Override
    public void close() {

    }
}
