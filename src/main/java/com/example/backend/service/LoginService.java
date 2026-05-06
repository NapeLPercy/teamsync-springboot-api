package com.example.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dao.AccountRepository;
import com.example.backend.dto.LoginRequest;
import com.example.backend.exception.InvalidCredentialsException;
import com.example.backend.model.*;
import com.example.backend.security.JwtService;

@Service
public class LoginService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginRequest loginUser(String email, String submittedPassword) {
        Optional<Account> account = accountRepository.loginUser(email);
        
        if (account.isEmpty())
            throw new InvalidCredentialsException("Invalid email or password");

        // compared saved password to subitted password
        boolean isValidUser = passwordEncoder.matches(submittedPassword, account.get().getPassword());

        if (!isValidUser)
            throw new InvalidCredentialsException("Invalid email or password");

        String role = account.get().getUserRole().toString();
        String token = jwtService.generateToken(email, role);
        
        return new LoginRequest(email,role, token);
    }

}