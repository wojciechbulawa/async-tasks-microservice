package com.example.async.tasks.mappers;

import com.example.async.tasks.dto.HelloMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HelloMessageMapper {

    HelloMessage toMsg(String message);
}
