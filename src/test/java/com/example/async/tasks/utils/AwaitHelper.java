package com.example.async.tasks.utils;

import com.example.async.tasks.entity.Status;
import com.example.async.tasks.repository.TestTaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AwaitHelper {

    private final TestTaskRepository testTaskRepository;
    private final TestAwaitProperties props;

    public void awaitTaskCompleted(@NonNull Long id) {
        awaitTask(id, Status.COMPLETED);
    }

    public void awaitTaskStarted(@NonNull Long id) {
        awaitTask(id, Status.STARTED);
    }

    public void awaitTasksStarted(int count) {
        defaultFactory()
                .until(() -> testTaskRepository.countWithStatus(Status.STARTED) == count);
    }

    private void awaitTask(@NonNull Long id, @NonNull Status status) {
        defaultFactory()
                .until(() -> status.equals(testTaskRepository.findStatusOf(id)));
    }

    private ConditionFactory defaultFactory() {
        return Awaitility.await()
                .atMost(props.atMostSec().getSeconds(), TimeUnit.SECONDS)
                .pollDelay(props.pollDelayMs().toMillis(), TimeUnit.MILLISECONDS)
                .pollInterval(props.pollIntervalMs().toMillis(), TimeUnit.MILLISECONDS);
    }
}
