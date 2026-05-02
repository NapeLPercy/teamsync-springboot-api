package com.example.backend.model;

import java.util.*;
import java.time.LocalDateTime;

public class Account {
    private String id;
    private String email;
    private String password;
    private String status;
    private LocalDateTime createdAt;
    private String userId;

    public Account() {
    }

    public Account(String id, String email, String password, String status, String userId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
