package com.example.async.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String status;
    private BigDecimal percentage;
    private Integer position;
    private Integer typos;
    private String pattern;
    private String input;
}
