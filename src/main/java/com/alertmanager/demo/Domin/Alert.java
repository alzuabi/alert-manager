package com.alertmanager.demo.Domin;

import com.alertmanager.demo.Utils.StringMapConverter;
import org.springframework.core.serializer.Serializer;

import javax.persistence.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Entity(name = "Alert")
public class Alert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "AlertType")
    private String alertType;

    @Column(name = "OnTimestamp")
    private Timestamp on;


    @Convert(converter = StringMapConverter.class)
    private Map<String, Object> context;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "action_id")
//    private Action action;

    public Alert() {
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

    public Timestamp getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = Timestamp.valueOf(on);
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }


//    public Action getAction() {
//        return action;
//    }
//
//    public void setAction(Action action) {
//        this.action = action;
//    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", alertType='" + alertType + '\'' +
                ", on=" + on +
                ", context=" + context +
                '}';
    }

//    @Override
//    public void serialize(Alert alert, OutputStream outputStream) throws IOException {
//
//    }
//
//    @Override
//    public byte[] serializeToByteArray(Alert object) throws IOException {
//        return new byte[0];
//    }
}
