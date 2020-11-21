package com.alertmanager.demo.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alert-manager")
public class AlertManagerConfig {
    private Topic alertsTopic;

    public Topic getAlertsTopic() {
        return alertsTopic;
    }

    public void setAlertsTopic(Topic alertsTopic) {
        this.alertsTopic = alertsTopic;
    }


    public static class Topic {
        private String name;
        private Integer partitions = 3;
        private Integer replicas = 2;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPartitions() {
            return partitions;
        }

        public void setPartitions(Integer partitions) {
            this.partitions = partitions;
        }

        public Integer getReplicas() {
            return replicas;
        }

        public void setReplicas(Integer replicas) {
            this.replicas = replicas;
        }
    }
}
