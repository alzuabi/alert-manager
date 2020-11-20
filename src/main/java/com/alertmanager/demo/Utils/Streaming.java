package com.alertmanager.demo.Utils;

import com.alertmanager.demo.Domin.Alert;
import com.alertmanager.demo.Service.AlertService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Streaming {
    private final AlertService alertService;
    //    private final KStream<?,?> kStream;
    @Value("${BOOTSTRAP_SERVERS}")
    private String  BOOTSTRAP_SERVERS;
    @Value("${APPLICATION_ID}")
    private String APPLICATION_ID;

//    public Streaming(KStream<?, ?> kStream){
//        this.kStream=kStream;
//    }
    Streaming(AlertService alertService){
        this.alertService=alertService;
    }
    public void startStreaming(String inputTopic, String outputTopic) {
//        System.out.println("** STARTING STREAM APP **");
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
//        props.put("bootstrap.servers","localhost:9092");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AlertSeder.class);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Alert> source =builder.stream(inputTopic);

        KStream<String, Alert> output = source.map((key, alert) -> {
//            System.out.println("**BEFORE- Alert save: " + alert.toString());
            if(alert!=null)
                alertService.save(alert);
//            System.out.println("**AFTER- Alert save: " + alert.toString());
            return KeyValue.pair(key, alert);
        });
        output.to(outputTopic);


        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
