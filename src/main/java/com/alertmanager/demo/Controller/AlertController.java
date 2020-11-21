package com.alertmanager.demo.Controller;

import com.alertmanager.demo.Domin.Alert;
import com.alertmanager.demo.Service.AlertService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/alert")
public class AlertController {
    private final AlertService alertService;


    AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping()
    @ResponseBody
    public Page<Alert> getAllAlerts(Pageable pageable) {
        return alertService.findAll(pageable);
    }

    @GetMapping("/{alertId}")
    @ResponseBody
    public ResponseEntity<Alert> getAlertById(@PathVariable long alertId) {
        return alertService.findById(alertId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Alert saveAlert(@RequestBody Alert alert) {
        return alertService.save(alert);
    }

    @PutMapping("/{alertId}")
    public ResponseEntity<Alert> updateAlert(@PathVariable long alertId, @RequestBody Alert alertU) {
        return alertService.findById(alertId).map(alert -> {
            alert.setAlertType(alertU.getAlertType());
            alert.setOn(alertU.getOn().toString());
            alert.setContext(alertU.getContext());
            return ResponseEntity.ok(alertService.save(alert));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<?> deleteAction(@PathVariable long alertId) {
        return alertService.findById(alertId).map(alert -> {
            alertService.delete(alert);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/query")
    @ResponseBody
    public List<Alert> getAlertsBetween(@RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "after", defaultValue = "#{alertService.getOldestTime()}") Timestamp after,
                                        @RequestParam(value = "before", defaultValue = "#{alertService.getCurrentTime()}") Timestamp before) {
        if (type == null)
            return alertService.getAlertsBetween(after, before).orElse(Collections.emptyList());
        else
            return alertService.findAllByOnBetweenAndAlertTypeEquals(after, before, type).orElse(Collections.emptyList());
    }


}

