package com.alertmanager.demo.Utils;

import com.alertmanager.demo.Domin.Alert;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;

public final class CustomSerdes {

    static public final class AlertSerde
            extends Serdes.WrapperSerde<Alert> {
        public AlertSerde() {
            super(new JsonSerializer<>(),
                    new JsonDeserializer<>(Alert.class));
        }
    }

//    static public final class GenreCountSerde
//            extends Serdes.WrapperSerde<GenreCount> {
//        public GenreCountSerde() {
//            super(new JsonSerializer<>(),
//                    new JsonDeserializer<>(GenreCount.class));
//        }
//    }

    public static Serde<Alert> alert() {
        return new AlertSerde();
    }

//    public static Serde<GenreCount> GenreCount() {
//        return new GenreCountSerde();
//    }
}
