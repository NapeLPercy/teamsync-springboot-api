package com.example.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatusType status;
    private TaskPriorityType priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String projectId;
    private String assignedTo;
    private String assignedBy;

    public Task(String id, String title, String description, TaskStatusType status, TaskPriorityType priority,
            LocalDate dueDate, String assignedTo, String assignedBy, String projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.projectId = projectId;
        this.dueDate = dueDate;
    }

    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatusType getStatus() {
        return status;
    }

    public void setStatus(TaskStatusType status) {
        this.status = status;
    }

    public TaskPriorityType getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityType priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssigned_to(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssigned_by(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
                + ", priority=" + priority + ", dueDate=" + dueDate + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + ", projectId=" + projectId + ", assignedTo=" + assignedTo + ", assignedBy="
                + assignedBy + "]";
    }

}
