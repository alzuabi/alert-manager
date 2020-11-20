package com.alertmanager.demo.Controller;

import com.alertmanager.demo.Domin.Action;
import com.alertmanager.demo.Service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/action")
public class ActionController {
    private ActionService actionService;

    @Autowired
    ActionController(ActionService actionService){
        this.actionService = actionService;
    }
    @GetMapping()
    @ResponseBody
    public List<Action> getAllActions()
    {
        return actionService.findAll();
    }

    @GetMapping("/{actionId}")
    @ResponseBody
    public ResponseEntity<Optional<Action>> getActionById(@PathVariable long actionId)
    {
        Optional<Action> actionAlert = Optional.ofNullable(actionService.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found for this id :: " + actionId)));
        return ResponseEntity.ok().body(actionAlert);
    }

    @PostMapping()
    public Action saveAction(@RequestBody Action action){
        return actionService.save(action);
    }

    @PutMapping("/{actionId}")
    public Action updateAction(@PathVariable long actionId, @RequestBody Action actionU) {
        return actionService.findById(actionId).map(action -> {
            action.setActionName(actionU.getActionName());
            action.setAlertType(actionU.getAlertType());
            action.setConfig(actionU.getConfig());
            return actionService.save(action);
        }).orElseThrow(() -> new ResourceNotFoundException("Action not found for this id ::  " + actionId));
    }


    @DeleteMapping("/{actionId}")
    public ResponseEntity<?> deleteAction(@PathVariable long actionId) {
        return actionService.findById(actionId).map(action -> {
            actionService.delete(action);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Action not found for this id ::  " + actionId));
    }


}

