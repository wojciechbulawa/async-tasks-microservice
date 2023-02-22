package com.example.async.tasks.service.cache;

import com.example.async.tasks.service.ProcessedText;
import com.example.async.tasks.service.ProcessingTask;
import com.example.async.tasks.service.ProcessingTaskData;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "tasks.cache.enabled", havingValue = "true")
class CaffeineTasksCache implements TasksCache {

    private final Cache<ProcessingTaskData, ProcessedText> cache;

    CaffeineTasksCache(TasksCacheProperties properties) {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(properties.entryLifetime())
                .build();
    }

    @Override
    public void put(ProcessingTask task, ProcessedText result) {
        cache.put(task.toData(), result);
    }

    @Override
    public ProcessedText get(ProcessingTask task) {
        return cache.getIfPresent(task.toData());
    }
}
