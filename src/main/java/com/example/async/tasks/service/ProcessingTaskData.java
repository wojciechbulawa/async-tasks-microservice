package com.example.async.tasks.service;

import lombok.NonNull;

public record ProcessingTaskData(@NonNull String pattern,
                                 @NonNull String text) {
}
