package com.example.backend.dto;

public record RegisterRequest(
        // company data
        String name,
        String companyId,
        
        // user data
        String fullName,
        // account data
        String email,
        String password,
        String status,
        // role data
        String role) {
}