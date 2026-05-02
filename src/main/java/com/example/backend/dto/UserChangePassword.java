package com.example.backend.dto;

public class UserChangePassword {
    private String userId;
    private String newPassword;

    public UserChangePassword(String userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
