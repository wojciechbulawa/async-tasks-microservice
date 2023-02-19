package com.example.async.tasks.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class InMemoryUser {
    @NonNull
    @NotBlank
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Set<String> roles = new HashSet<>();
}
