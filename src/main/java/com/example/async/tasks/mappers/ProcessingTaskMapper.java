package com.example.async.tasks.mappers;

import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.service.ProcessingTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProcessingTaskMapper {

    @Mapping(target = "id", source = "response.id")
    @Mapping(target = "pattern", source = "request.pattern")
    @Mapping(target = "text", source = "request.input")
    ProcessingTask toTask(TaskRequestDto request, TaskResponseDto response);
}
