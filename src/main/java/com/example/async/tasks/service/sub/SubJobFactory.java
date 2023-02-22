package com.example.async.tasks.service.sub;

import com.example.async.tasks.service.TaskService;
import com.example.async.tasks.service.TaskSplitter;
import com.example.async.tasks.service.executor.SleepHelper;
import org.springframework.stereotype.Component;

@Component
public class SubJobFactory {

    private final SubTaskProcessor subTaskProcessor;
    private final TaskService taskService;
    private final SleepHelper sleepHelper;

    public SubJobFactory(TaskService taskService, SleepHelper sleepHelper) {
        this.subTaskProcessor = new SubTaskProcessor();
        this.taskService = taskService;
        this.sleepHelper = sleepHelper;
    }

    public SubJob create(SubTask subTask, TaskSplitter splitter) {
        return new SubJob(subTask,
                splitter.getSubTaskPercentagePoints(),
                subTaskProcessor,
                prepareProgressUpdater(splitter),
                sleepHelper);
    }

    private ProgressUpdater prepareProgressUpdater(TaskSplitter splitter) {
        return (percentage) -> taskService.addPercentage(splitter.getMainTask(), percentage);
    }
}
