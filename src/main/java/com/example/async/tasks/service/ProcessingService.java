package com.example.async.tasks.service;

import com.example.async.tasks.service.executor.ThreadPoolExecutorFactory;
import com.example.async.tasks.service.sub.SubJobFactory;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class ProcessingService {

    private final TaskService taskService;
    private final ExecutorService mainExecutor;
    private final ExecutorService subExecutor;
    private final ThreadPoolExecutorFactory poolExecutorFactory;
    private final SubJobFactory subJobFactory;

    public ProcessingService(TaskService taskService,
                             ThreadPoolExecutorFactory poolExecutorFactory,
                             TasksThreadPoolProperties properties,
                             SubJobFactory subJobFactory) {
        this.taskService = taskService;
        this.mainExecutor = poolExecutorFactory.create(properties.mainThreadPool(), "main");
        this.subExecutor = poolExecutorFactory.create(properties.subThreadPool(), "sub");
        this.poolExecutorFactory = poolExecutorFactory;
        this.subJobFactory = subJobFactory;
    }

    public void process(ProcessingTask task) {
        MainJob job = MainJob.create(task,
                subExecutor,
                taskService,
                subJobFactory);
        mainExecutor.submit(job);
    }

    @PreDestroy
    void preDestroy() {
        poolExecutorFactory.shutdown(mainExecutor);
        poolExecutorFactory.shutdown(subExecutor);
    }

}
