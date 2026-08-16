package com.example.backend.dto;

import java.time.LocalDateTime;

public record Employee(
        String userId,
        String fullName,
        String role,
        boolean isActive,
        String email,
        String status,
        LocalDateTime createdAt) {
}
