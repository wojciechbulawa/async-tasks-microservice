package com.example.async.tasks.utils;

import com.example.async.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class DbCleaner {

    private final TaskRepository taskRepository;

    @Transactional
    void truncate() {
        taskRepository.deleteAll();
    }
}
