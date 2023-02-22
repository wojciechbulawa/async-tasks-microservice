package com.example.async.tasks.service;

import com.example.async.tasks.service.sub.SubJobFactory;
import com.example.async.tasks.service.sub.SubTask;
import com.example.async.tasks.service.sub.SubTaskResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MainJob implements Runnable {

    private final TaskSplitter splitter;
    private final ExecutorService executor;
    private final TaskService taskService;
    private final SubJobFactory factory;

    public static MainJob create(ProcessingTask mainTask,
                                 ExecutorService executor,
                                 TaskService taskService,
                                 SubJobFactory factory) {
        return new MainJob(
                new TaskSplitter(mainTask),
                executor,
                taskService,
                factory);
    }

    @Override
    public void run() {
        taskService.start(splitter.getMainTask());
        List<Future<SubTaskResult>> futures = splitter.split()
                .map(this::submitSubJob)
                .toList();

        SortedSet<SubTaskResult> results = getResults(futures);

        ProcessedText bestMatch = results.first().toProcessedText();
        taskService.complete(splitter.getMainTask(), bestMatch);
    }

    private Future<SubTaskResult> submitSubJob(SubTask subTask) {
        return executor.submit(factory.create(subTask, splitter));
    }

    private SortedSet<SubTaskResult> getResults(List<Future<SubTaskResult>> futures) {
        return futures.stream()
                .map(this::awaitFinish)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private SubTaskResult awaitFinish(Future<SubTaskResult> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            log.error("Subtask failed due to", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Subtask failed due to", e);
        }
        return null;
    }
}
