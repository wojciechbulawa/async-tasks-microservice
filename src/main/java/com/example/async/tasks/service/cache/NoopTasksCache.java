package com.example.async.tasks.service.cache;

import com.example.async.tasks.service.ProcessedText;
import com.example.async.tasks.service.ProcessingTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "tasks.cache.enabled", havingValue = "false")
class NoopTasksCache implements TasksCache {

    @Override
    public void put(ProcessingTask task, ProcessedText result) {
    }

    @Override
    public ProcessedText get(ProcessingTask task) {
        return null;
    }
}
