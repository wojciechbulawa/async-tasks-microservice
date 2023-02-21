package com.example.async.tasks.service;

import com.example.async.tasks.TaskRepository;
import com.example.async.tasks.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    private final TaskRepository repository;

    @Transactional
    public void process(ProcessingTask task) {

        int position = 1;
        int typos = 1;

        completeTask(task, position, typos);
    }

    private void completeTask(ProcessingTask task, int position, int typos) {
        repository.findById(task.id())
                .map(entity -> {
                    entity.setStatus(Status.COMPLETED);
                    entity.setPercentage(100);
                    entity.setPosition(position);
                    entity.setTypos(typos);
                    return entity;
                });
    }

}
