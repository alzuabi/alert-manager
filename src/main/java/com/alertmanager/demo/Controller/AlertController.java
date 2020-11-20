package com.alertmanager.demo.Controller;

import com.alertmanager.demo.Domin.Alert;
import com.alertmanager.demo.Service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/alert")
public class AlertController {
    private AlertService alertService;
//    private ActionService actionService;


    @Autowired
    AlertController(AlertService alertService
//            ,ActionService actionService
    ) {
        this.alertService = alertService;
//        this.actionService = actionService;
    }

    @GetMapping()
    @ResponseBody
    public Page<Alert> getAllAlerts(Pageable pageable) {
        return alertService.findAll(pageable);
    }

    @GetMapping("/{alertId}")
    @ResponseBody
    public ResponseEntity<Optional<Alert>> getAlertById(@PathVariable long alertId) {
        Optional<Alert> alert = Optional.ofNullable(alertService.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found for this id :: " + alertId)));
        return ResponseEntity.ok().body(alert);
    }

    @PostMapping()
    public Alert saveAlert(@RequestBody Alert alert) {
        return alertService.save(alert);
    }

    @PutMapping("/{alertId}")
    public Alert updateAlert(@PathVariable long alertId, @RequestBody Alert alertU) {
        return alertService.findById(alertId).map(alert -> {
            alert.setAlertType(alertU.getAlertType());
            alert.setOn(alertU.getOn().toString());
            alert.setContext(alertU.getContext());
            return alertService.save(alert);
        }).orElseThrow(() -> new ResourceNotFoundException("Alert not found for this id ::  " + alertId));
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<?> deleteAction(@PathVariable long alertId) {
        return alertService.findById(alertId).map(alert -> {
            alertService.delete(alert);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Alert not found for this id ::  " + alertId));
    }

    @GetMapping("/query")
    @ResponseBody
    public Optional<List<Alert>> getAlertsBetween(@RequestParam(value = "type", required = false) String type ,
                             @RequestParam(value = "after", defaultValue = "#{alertService.getOldestTime()}") Timestamp after,
                             @RequestParam(value = "before" ,defaultValue = "#{alertService.getCurrentTime()}") Timestamp before)
    {
        if (type==null)
            return alertService.getAlertsBetween(after,before);
        else
            return alertService.findAllByOnBetweenAndAlertTypeEquals(after,before,type);
    }



//    @GetMapping("/action/{actionId}/alert")
//    public Page<Alert> getAllAlertByActionId(@PathVariable(value = "actionId") Long actionId,
//                                             Pageable pageable) {
//        return alertService.findByActionId(actionId, pageable);
//    }

//    @GetMapping("/action/{actionId}/alert")
//    public Alert saveAlert(@PathVariable(value = "actionId") Long actionId,
//                           Alert alert) {
//
//        return actionService.findById(actionId).map(
//                action -> {
//                    alert.setAction(action);
//                    return alertService.save(alert);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("Action not found for this id :: " + actionId));
//    }

//    @PutMapping("/action/{actionId}/alert/{alertId}")
//    public Alert updateAlert(@PathVariable Long actionId,
//                              @PathVariable Long alertId,
//                              @RequestBody Alert alertU) {
//        if(!actionService.existsById(actionId)) {
//            throw new ResourceNotFoundException("Action not found for this id :: " + actionId);
//        }
//        return alertService.findById(alertId).map(alert -> {
//            alert.setAlertType(alertU.getAlertType());
//            alert.setOn(alertU.getOn());
//            alert.setContext(alertU.getContext());
//            return alertService.save(alert);
//        }).orElseThrow(() -> new ResourceNotFoundException("Alert not found for this id ::  " + alertId));
//    }

//    @DeleteMapping("/action/{actionId}/alert/{alertId}")
//    public ResponseEntity<?> deleteComment(@PathVariable (value = "actionId") Long actionId,
//                                           @PathVariable (value = "alertId") Long alertId) {
//        return alertService.findByIdAndActionId(alertId, actionId).map(alert -> {
//            alertService.delete(alert);
//            return ResponseEntity.ok().build();
//        }).orElseThrow(() -> new ResourceNotFoundException("Alert not found with id " + alertId + " and actionId " + actionId));
//    }


}

