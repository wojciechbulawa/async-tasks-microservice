package com.example.async.tasks.service;

import com.example.async.tasks.TaskRepository;
import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.Task;
import com.example.async.tasks.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    @Transactional
    public TaskResponseDto save(TaskRequestDto dto) {
        Task task = mapper.toEntity(dto);
        Task saved = repository.save(task);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<TaskDto> find(Long id) {
        Optional<Task> byId = repository.findById(id);
        if (byId.isEmpty()) {
            log.info("Found no task with id: {}", id);
            return Optional.empty();
        }

        log.info("Found task with id: {}", id);
        return Optional.of(mapper.toFullDto(byId.get()));
    }

}
