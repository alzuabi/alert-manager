package com.alertmanager.demo.Service;

import com.alertmanager.demo.Domin.Alert;
import com.alertmanager.demo.Service.AlertService;
import com.alertmanager.demo.Service.AlertsProcessor;
import com.alertmanager.demo.Utils.AlertManagerConfig;
import com.alertmanager.demo.Utils.JsonSerde;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Configuration
public class Streaming {
    @Bean
    public KafkaStreamsInfrastructureCustomizer streams(
            AlertsProcessor processor,
            AlertManagerConfig config
    ) {
        return new KafkaStreamsInfrastructureCustomizer() {
            @Override
            public void configureBuilder(StreamsBuilder builder) {
                builder.stream(config.getAlertsTopic().getName(), Consumed.with(
                        Serdes.String(),
                        JsonSerde.jsonSerde(Alert.class)
                )).process(() -> processor);
            }
        };
    }


    @Bean
    public NewTopic alertsTopic(AlertManagerConfig config) {
        return TopicBuilder.name(config.getAlertsTopic().getName())
                .partitions(config.getAlertsTopic().getPartitions())
                .replicas(config.getAlertsTopic().getReplicas())
                .build();
    }
}
