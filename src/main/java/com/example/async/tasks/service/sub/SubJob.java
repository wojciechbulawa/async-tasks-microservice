package com.example.async.tasks.service.sub;

import com.example.async.tasks.service.executor.SleepHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SubJob implements Callable<SubTaskResult> {

    private final SubTask subTask;
    private final BigDecimal percentagePoints;
    private final SubTaskProcessor subTaskProcessor;
    private final ProgressUpdater progressUpdater;
    private final SleepHelper sleepHelper;

    @Override
    public SubTaskResult call() throws Exception {
        sleepHelper.sleep();
        SubTaskResult processed = subTaskProcessor.process(subTask);
        progressUpdater.addProgressPercentage(percentagePoints);
        return processed;
    }
}
