package com.example.backend.model;

import java.time.LocalDateTime;
import java.util.*;

public class Comment {
    private String id;
    private String content;
    private LocalDateTime createdAt;

    private String userId;
    private String taskId;

    public Comment(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public Comment() {}

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
