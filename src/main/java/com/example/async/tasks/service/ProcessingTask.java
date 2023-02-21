package com.example.async.tasks.service;

import lombok.NonNull;

public record ProcessingTask(@NonNull Long id,
                             @NonNull String pattern,
                             @NonNull String text) {
}
