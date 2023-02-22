package com.example.async.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    private final TaskService taskService;
    private final TextProcessor textProcessor = new SimpleTextProcessor();

    public void process(ProcessingTask task) {
        ProcessedText processed = textProcessor.process(task.pattern(), task.text());
        taskService.complete(task, processed.position(), processed.typos());
    }

}
