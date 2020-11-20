package com.alertmanager.demo.Controller;

import com.alertmanager.demo.Service.OperationService;
import com.alertmanager.demo.Utils.Streaming;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@RestController
@RequestMapping("api/operation")
public class OperationsController {
    private final Streaming streaming;
    private final OperationService operationService;

    @Autowired
    OperationsController(Streaming streaming, OperationService operationService){
        this.streaming = streaming;
        this.operationService = operationService;
    }

    @PostMapping("/startStreaming")
    public void startStreaming(@RequestParam(value = "inputTopic" , defaultValue = "inputTopic") String inputTopic ,
                               @RequestParam(value = "outputTopic" , defaultValue = "outputTopic") String outputTopic)
    {
        streaming.startStreaming(inputTopic,outputTopic);
    }

    @PostMapping("/createTopic")
    public Map<String, KafkaFuture<Void>> createNewTopic(@RequestParam(value = "topicName") String topicName){
        return operationService.createNewTopic(topicName);
    }
}
