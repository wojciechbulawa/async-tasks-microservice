package com.example.async.tasks.rest;

import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.TaskStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TasksResource {

    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        log.info("Received /api/tasks/create request.\nPattern: {}\nInput: {}",
                taskRequestDto.getPattern(), taskRequestDto.getInput());
        TaskResponseDto taskResponseDto = new TaskResponseDto(1L);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskResponseDto);
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDto> get(@PathVariable Long id) {
        log.info("Received /api/tasks/{} request", id);
        TaskDto taskDto = TaskDto.builder()
                .id(id)
                .status(TaskStatus.RECEIVED.name())
                .percentage(0)
                .position(null)
                .typos(null)
                .pattern(null)
                .input(null)
                .build();

        return ResponseEntity.ok(taskDto);
    }

}
