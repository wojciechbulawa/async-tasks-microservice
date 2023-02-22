package com.example.async.tasks.service.executor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Validated
@Component
@RequiredArgsConstructor
public class ThreadPoolExecutorFactory {

    private final ThreadPoolProperties props;

    public ExecutorService create(@Min(1) int size, @NotBlank String name) {
        ThreadFactory factory = new NamedThreadFactory(name);
        return new ThreadPoolExecutor(size, size,
                props.keepAliveTime().toNanos(), TimeUnit.NANOSECONDS,
                new LinkedBlockingDeque<>(),
                factory);
    }

    public void shutdown(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(props.awaitTermination().toNanos(), TimeUnit.NANOSECONDS)) {
                executorService.shutdown();
            }
        } catch (InterruptedException e) {
            log.error("Failed to shutdown executor service {}", executorService, e);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Value
    private static class NamedThreadFactory implements ThreadFactory {

        String prefix;
        AtomicInteger counter = new AtomicInteger();

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, prefix + "-" + counter.getAndIncrement());
        }
    }
}
