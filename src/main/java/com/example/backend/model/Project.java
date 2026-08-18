package com.example.backend.model;

import java.time.*;
import java.util.*;

public class Project {

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
    private String category;
    private String userId;
    private String companyId;

    public Project(String id, String name, String description, String category, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.dueDate = dueDate;
    }

    public Project() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", name=" + name + ", description=" + description + ", createdAt=" + createdAt
                + ", userId=" + userId + ", companyId=" + companyId + "]";
    }

}
