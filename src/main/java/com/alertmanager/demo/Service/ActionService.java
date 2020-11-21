package com.alertmanager.demo.Service;

import com.alertmanager.demo.Domin.Action;
import com.alertmanager.demo.Repository.ActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService {
    private final ActionRepository actionRepository;

    ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public Optional<Action> findById(long id) {
        return actionRepository.findById(id);
    }

    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    public Action save(Action action) {
        return actionRepository.save(action);
    }

    public void delete(Action action) {
        actionRepository.delete(action);
    }

    public boolean existsById(long actionId) {
        return actionRepository.existsById(actionId);
    }

}
