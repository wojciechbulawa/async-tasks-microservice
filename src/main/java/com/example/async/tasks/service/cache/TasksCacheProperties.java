package com.example.async.tasks.service.cache;


import lombok.NonNull;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "tasks.cache")
record TasksCacheProperties(@NonNull Boolean enabled,
                            @NonNull @DurationMin(seconds = 1) Duration entryLifetime) {
}
