package com.alertmanager.demo.Utils;

import com.alertmanager.demo.Domin.Alert;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AlertSeder implements Serde<Alert> {
    private final GsonSerializer serializer = new GsonSerializer();
    private final GsonDeserializer deserializer = new GsonDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<Alert> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<Alert> deserializer() {
        return deserializer;
    }
}
