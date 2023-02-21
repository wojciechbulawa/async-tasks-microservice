package com.example.async.tasks.mappers;

import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskRequestDto dto);

    TaskResponseDto toResponse(Task task);

    TaskDto toFullDto(Task task);
}
