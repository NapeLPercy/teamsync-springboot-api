package com.example.backend.dto;

public record RegisterEmployeeRequest (
    // company data
        String companyName,
        String companyId,
        // user data
        String fullName,
        // account data
        String email,
        String password
        ){
    
}
