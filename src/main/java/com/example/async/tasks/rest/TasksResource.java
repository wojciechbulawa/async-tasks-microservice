package com.example.async.tasks.rest;

import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.mappers.ProcessingTaskMapper;
import com.example.async.tasks.message.MsgSender;
import com.example.async.tasks.service.TaskService;
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

    private final MsgSender msgSender;
    private final TaskService taskService;
    private final ProcessingTaskMapper processingTaskMapper;

    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        log.info("Received /api/tasks/create request.\nPattern: {}\nInput: {}",
                taskRequestDto.getPattern(), taskRequestDto.getInput());
        TaskResponseDto response = taskService.save(taskRequestDto);
        msgSender.sendTask(processingTaskMapper
                .toTask(taskRequestDto, response));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDto> get(@PathVariable Long id) {
        log.info("Received /api/tasks/{} request", id);

        return taskService.find(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
