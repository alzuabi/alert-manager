package com.alertmanager.demo.Controller;

import com.alertmanager.demo.Domin.Action;
import com.alertmanager.demo.Service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/action")
public class ActionController {
    private final ActionService actionService;

    @Autowired
    ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping()
    @ResponseBody
    public List<Action> getAllActions() {
        return actionService.findAll();
    }

    @GetMapping("/{actionId}")
    @ResponseBody
    public ResponseEntity<Action> getActionById(@PathVariable long actionId) {
        return actionService.findById(actionId).map(
                ResponseEntity::ok
        ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Action saveAction(@RequestBody Action action) {
        return actionService.save(action);
    }

    @PutMapping("/{actionId}")
    public ResponseEntity<Action> updateAction(@PathVariable long actionId, @RequestBody Action actionU) {
        return actionService.findById(actionId).map(action -> {
            action.setActionName(actionU.getActionName());
            action.setAlertType(actionU.getAlertType());
            action.setConfig(actionU.getConfig());
            return ResponseEntity.ok(actionService.save(action));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{actionId}")
    public ResponseEntity<?> deleteAction(@PathVariable long actionId) {
        return actionService.findById(actionId).map(action -> {
            actionService.delete(action);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }


}

