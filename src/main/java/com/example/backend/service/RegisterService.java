package com.example.backend.service;

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
    private RoleRepository RoleRepository;

    public RegisterService(CompanyRepository companyRepository, UserRepository userRepository,
            AccountRepository accountRepository, RoleRepository RoleRepository) {

        this.RoleRepository = RoleRepository;
        this.accountRepository = accountRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createCompany(RegisterRequest req) {
        UUID companyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        Company company = new Company(companyId.toString(), req.name());

        User user = new User(userId.toString(), req.fullName(), companyId.toString());

        Account account = new Account(accountId.toString(), req.email(), req.password(), req.status(),
                userId.toString());

        int saveCompanyResult = companyRepository.saveCompany(company);
        int saveUserResult = userRepository.saveUser(user);
        int saveAccountResult = accountRepository.saveAccount(account);
        int saveRoleResult = RoleRepository.saveRole(roleId.toString(), req.role(), userId.toString());

    }

    @Transactional
    public void createEmployee(RegisterRequest req) {
        String companyId = req.companyId();// taken from req
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User user = new User(userId.toString(), req.fullName(), companyId);

        Account account = new Account(accountId.toString(), req.email(), req.password(), req.status(),
                userId.toString());

        int saveUserResult = userRepository.saveUser(user);
        int saveAccountResult = accountRepository.saveAccount(account);
        int saveRoleResult = RoleRepository.saveRole(roleId.toString(), req.role(), userId.toString());

    }

}