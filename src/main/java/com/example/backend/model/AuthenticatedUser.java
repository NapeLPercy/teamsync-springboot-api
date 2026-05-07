package com.example.backend.model;

public class AuthenticatedUser {

    private String userId;
    private String email;
    private String role;

    public AuthenticatedUser(String userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "AuthenticatedUser [userId=" + userId + ", email=" + email + ", role=" + role + ", getUserId()="
                + getUserId() + ", getEmail()=" + getEmail() + ", getRole()=" + getRole() + "]";
    }

    
}
