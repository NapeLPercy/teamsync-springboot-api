package com.example.backend.model;

import java.time.LocalDateTime;
import java.util.*;

public class Comment {
    private String id;
    private String content;
    private LocalDateTime createdAt;

    private String submittedBy;
    private String taskId;

    public Comment(String id, String content, String taskId) {
        this.id = id;
        this.content = content;
        this.taskId = taskId;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
