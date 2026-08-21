package com.example.backend.dto;

import java.time.LocalDateTime;

public record CommentsResponse(
        String id,
        String content,
        LocalDateTime createdAt,
        String taskId,
        String fullName,
        String role
) {}