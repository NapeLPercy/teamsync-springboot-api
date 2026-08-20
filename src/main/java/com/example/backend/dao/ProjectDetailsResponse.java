package com.example.backend.dao;

import java.time.LocalDate;

public record ProjectDetailsResponse(
        String id,
        String name,
        LocalDate dueDate) {

}
