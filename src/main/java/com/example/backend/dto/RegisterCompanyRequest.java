package com.example.backend.dto;

public record RegisterCompanyRequest(
        // company data
        String companyName, 
        // user data
        String fullName,
        // account data
        String email,
        String password
        ) {
}