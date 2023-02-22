package com.example.async.tasks.service;

import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.Status;
import com.example.async.tasks.entity.Task;
import com.example.async.tasks.mappers.TaskMapper;
import com.example.async.tasks.repository.TaskRepository;
import com.example.async.tasks.service.cache.TasksCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private static final BigDecimal MAX_PERCENTAGE = BigDecimal.valueOf(100);

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final TasksCache tasksCache;

    @Transactional
    public TaskResponseDto save(TaskRequestDto dto) {
        Task task = mapper.toEntity(dto);
        Task saved = repository.save(task);
        log.info("Saved task[id={}]", saved.getId());
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
        log.info("Updated task[id={}] as started", task.id());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addPercentage(ProcessingTask task, BigDecimal points) {
        repository.findById(task.id())
                .map(entity -> {
                    BigDecimal current = entity.getPercentage();
                    BigDecimal sum = current.add(points);
                    if (lessOrEqualToMax(sum)) {
                        entity.setPercentage(sum);
                        log.info("Updated task[id={}] progress: {}", task.id(), sum);
                    }
                    return entity;
                });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void complete(ProcessingTask task, ProcessedText result) {
        repository.findById(task.id())
                .map(entity -> {
                    entity.setStatus(Status.COMPLETED);
                    entity.setPercentage(MAX_PERCENTAGE);
                    entity.setPosition(result.position());
                    entity.setTypos(result.typos());
                    return entity;
                });
        tasksCache.put(task, result);
        log.info("Updated task[id={}] as completed", task.id());
    }

    private static boolean lessOrEqualToMax(BigDecimal percentage) {
        return MAX_PERCENTAGE.compareTo(percentage) >= 0;
    }

}
