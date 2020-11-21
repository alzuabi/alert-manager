package com.alertmanager.demo.Service;

import com.alertmanager.demo.Domin.Alert;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

@Component
public class AlertsProcessor implements Processor<String, Alert> {
    private ProcessorContext ctx;

    private final AlertService alerts;

    public AlertsProcessor(AlertService alerts) {
        this.alerts = alerts;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.ctx = processorContext;
    }

    @Override
    public void process(String s, Alert alert) {
        alerts.save(alert);
    }

    @Override
    public void close() {
        this.ctx.commit();
    }
}
