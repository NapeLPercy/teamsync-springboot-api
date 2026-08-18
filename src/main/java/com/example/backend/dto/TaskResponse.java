package com.example.backend.dto;

import java.time.LocalDateTime;

import com.example.backend.model.TaskPriorityType;
import com.example.backend.model.TaskStatusType;

public record TaskResponse(
        String id,
        String title,
        String description,
        TaskStatusType status,
        TaskPriorityType priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt) {
}
