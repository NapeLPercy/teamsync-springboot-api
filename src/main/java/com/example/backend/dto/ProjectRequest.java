package com.example.backend.dto;

import java.time.LocalDate;

public record ProjectRequest(
        String name,
        String description,
        String category,
        LocalDate dueDate ) {
}