package com.alertmanager.demo.Service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
@Service
public class OperationService {
    public Map<String, KafkaFuture<Void>> createNewTopic(String topicName){
        Properties properties = new Properties();
//        properties.load(new FileReader(new File("kafka.properties")));
        properties.put("bootstrap.servers","localhost:9092");
        AdminClient adminClient = AdminClient.create(properties);
        NewTopic newTopic = new NewTopic(topicName, 1, (short)1); //new NewTopic(topicName, numPartitions, replicationFactor)

        List<NewTopic> newTopics = new ArrayList<>();
        newTopics.add(newTopic);


        CreateTopicsResult result = adminClient.createTopics(newTopics);
        adminClient.close();
        return result.values();
    }
}
