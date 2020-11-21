package com.alertmanager.demo.Utils;

import com.alertmanager.demo.Domin.Alert;


public class AlertSeder extends JsonSerde<Alert> {
    public AlertSeder() {
        super(Alert.class);
    }
}
