package com.example.async.tasks.service;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "tasks")
record TasksThreadPoolProperties(@Min(1) Integer mainThreadPool,
                                 @Min(1) Integer subThreadPool) {
}
