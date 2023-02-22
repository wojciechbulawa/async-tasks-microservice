package com.example.async.tasks.service;

import com.example.async.tasks.service.executor.ThreadPoolExecutorFactory;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class ProcessingService {

    private final TaskService taskService;
    private final TextProcessor textProcessor;
    private final ExecutorService mainExecutor;
    private final ThreadPoolExecutorFactory factory;

    public ProcessingService(TaskService taskService,
                             ThreadPoolExecutorFactory factory) {
        this.taskService = taskService;
        this.textProcessor = new SimpleTextProcessor();
        this.mainExecutor = factory.create(2, "mainProcessor");
        this.factory = factory;
    }

    public void process(ProcessingTask task) {
        mainExecutor.submit(() -> {
            taskService.start(task);
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ProcessedText processed = textProcessor.process(task.pattern(), task.text());
            taskService.complete(task, processed.position(), processed.typos());
        });
    }

    @PreDestroy
    void preDestroy() {
        factory.shutdown(mainExecutor);
    }

}
