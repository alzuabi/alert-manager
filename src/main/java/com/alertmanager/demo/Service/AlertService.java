package com.alertmanager.demo.Service;

import com.alertmanager.demo.Domin.Alert;
import com.alertmanager.demo.Repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
    private final AlertRepository alertRepository;

    @Autowired
    AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Optional<Alert> findById(long id) {
        return alertRepository.findById(id);
    }

    public Page<Alert> findAll(Pageable pageable) {
        return alertRepository.findAll(pageable);
    }

    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }

    //    public Page<Alert> findByActionId(long actionId, Pageable pageable)
//    {
//        return alertRepository.findByAction_Id(actionId, pageable);
//    }
//
//    public Optional<Alert> findByIdAndActionId(long alertId, long actionId){
//        return alertRepository.findByIdAndAction_Id(alertId,actionId);
//    }
    public void delete(Alert alert) {
        alertRepository.delete(alert);
    }

    public Optional<List<Alert>> getAlertsBetween(Timestamp after, Timestamp before) {
        return alertRepository.findAllByOnBetween(after, before);
    }

    public Timestamp getOldestTime() {
        return alertRepository.findFirstByOrderByOn().getOn();
    }

    public Optional<List<Alert>> findAllByOnBetweenAndAlertTypeEquals(Timestamp after, Timestamp before, String alertType){
        return alertRepository.findAllByOnBetweenAndAlertTypeEquals(after,before,alertType);
    }

    public Timestamp getCurrentTime() {
        return new Timestamp(new Date().getTime());
    }
}
