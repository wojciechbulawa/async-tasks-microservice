package com.example.async.tasks.service;

import com.example.async.tasks.repository.TaskRepository;
import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.Status;
import com.example.async.tasks.entity.Task;
import com.example.async.tasks.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void start(ProcessingTask task) {
        repository.findById(task.id())
                .map(entity -> {
                    entity.setStatus(Status.STARTED);
                    return entity;
                });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addPercentage(ProcessingTask task, int points) {
        repository.findById(task.id())
                .map(entity -> {
                    Integer percentage = entity.getPercentage();
                    if (percentage < 100) {
                        entity.setPercentage(percentage + points);
                    }
                    return entity;
                });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void complete(ProcessingTask task, int position, int typos) {
        repository.findById(task.id())
                .map(entity -> {
                    entity.setStatus(Status.COMPLETED);
                    entity.setPercentage(100);
                    entity.setPosition(position);
                    entity.setTypos(typos);
                    return entity;
                });
    }

}
