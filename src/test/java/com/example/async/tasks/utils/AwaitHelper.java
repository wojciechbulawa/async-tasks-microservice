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

    public void awaitTaskCompleted(@NonNull Long id) {
        awaitTask(id, Status.COMPLETED);
    }

    public void awaitTaskStarted(@NonNull Long id) {
        awaitTask(id, Status.STARTED);
    }

    private void awaitTask(@NonNull Long id, @NonNull Status status) {
        defaultFactory()
                .until(() -> status.equals(testTaskRepository.findStatusOf(id)));
    }

    private static ConditionFactory defaultFactory() {
        return Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .pollDelay(10, TimeUnit.MILLISECONDS)
                .pollInterval(10, TimeUnit.MILLISECONDS);
    }
}
