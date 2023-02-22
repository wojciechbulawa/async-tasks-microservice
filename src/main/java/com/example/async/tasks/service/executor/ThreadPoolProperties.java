package com.example.async.tasks.service.executor;


import lombok.NonNull;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "executors.thread-pool")
record ThreadPoolProperties(
        @NonNull @DurationMin(seconds = 1) Duration keepAliveTime,
        @NonNull @DurationMin(seconds = 5) Duration awaitTermination) {
}
