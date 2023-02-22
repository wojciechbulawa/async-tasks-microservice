package com.example.async.tasks.service.sub;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record SubTask(@Min(1) int startIndex,
                      @NotBlank String pattern,
                      @NotBlank String text) {
}
