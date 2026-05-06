package com.example.backend.service;

import java.util.*;

import org.springframework.stereotype.Service;
import com.example.backend.dao.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

       public Optional<String> findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
       }
}