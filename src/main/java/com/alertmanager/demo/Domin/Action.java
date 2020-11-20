package com.alertmanager.demo.Domin;

import com.alertmanager.demo.Utils.StringMapConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity(name = "Action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "AlertType")
    private String alertType;

    private String actionName;


    @Convert(converter = StringMapConverter.class)
    private Map<String, Object> config;

//    public List<Alert> getAlerts() {
//        return alerts;
//    }
//
//    public void setAlerts(List<Alert> alerts) {
//        this.alerts = alerts;
//    }
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "action")
//    private List<Alert> alerts;

    public Action() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String action) {
        this.actionName = action;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "ActionAlert{" +
                "id=" + id +
                ", alertType='" + alertType + '\'' +
                ", action='" + actionName + '\'' +
                ", config=" + config +
                '}';
    }
}
