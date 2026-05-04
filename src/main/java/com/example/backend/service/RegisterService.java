package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dao.*;
import com.example.backend.dao.UserRepository;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.model.*;
import java.util.*;

@Service
public class RegisterService {

    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public RegisterService(CompanyRepository companyRepository, UserRepository userRepository,
            AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createCompany(RegisterRequest req) {
        UUID companyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        Company company = new Company(companyId.toString(), req.name());

        User user = new User(userId.toString(), req.fullName(), companyId.toString());

        String hashedPassword = passwordEncoder.encode(req.password());

        Account account = new Account(accountId.toString(), req.email(), hashedPassword, req.status(),
                userId.toString());

        companyRepository.saveCompany(company);
        userRepository.saveUser(user);
        accountRepository.saveAccount(account);
        roleRepository.saveRole(roleId.toString(), req.role(), userId.toString());

    }

    @Transactional
    public void createEmployee(RegisterRequest req) {
        String companyId = req.companyId();// taken from req
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User user = new User(userId.toString(), req.fullName(), companyId);

        String hashedPassword = passwordEncoder.encode(req.password());

        Account account = new Account(accountId.toString(), req.email(), hashedPassword, req.status(),
                userId.toString());

        userRepository.saveUser(user);
        accountRepository.saveAccount(account);
        roleRepository.saveRole(roleId.toString(), req.role(), userId.toString());

    }

}