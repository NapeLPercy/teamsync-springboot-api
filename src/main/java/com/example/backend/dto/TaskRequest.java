package com.example.backend.dto;

import java.time.LocalDate;

import com.example.backend.model.TaskPriorityType;
import com.example.backend.model.TaskStatusType;

public record TaskRequest(
        String title,
        String description,
        TaskPriorityType priority,
        LocalDate dueDate,
        String projectId,
        String assignedTo) {
}
