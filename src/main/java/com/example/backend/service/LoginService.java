package com.example.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dao.AccountRepository;
import com.example.backend.model.*;

@Service
public class LoginService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void loginUser(String email, String submittedPassword) {
        Optional<Account> account = accountRepository.loginUser(email);
        if (account.isEmpty())
            return;

        boolean isValidUser = passwordEncoder.matches(submittedPassword, account.get().getPassword());

        // compared saved password to subitted password
        if (isValidUser) {
            System.out.println("VAlid system user");
        } else {
            System.out.println("Not valid system user");
        }
    }

}