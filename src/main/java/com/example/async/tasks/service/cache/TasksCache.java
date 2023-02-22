package com.example.async.tasks.service.cache;


import com.example.async.tasks.service.ProcessedText;
import com.example.async.tasks.service.ProcessingTask;

public interface TasksCache {

    void put(ProcessingTask task, ProcessedText result);

    ProcessedText get(ProcessingTask task);
}
