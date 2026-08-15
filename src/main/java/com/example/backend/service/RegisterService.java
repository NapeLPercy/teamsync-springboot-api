package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dao.*;
import com.example.backend.dao.UserRepository;
import com.example.backend.dto.RegisterCompanyRequest;
import com.example.backend.dto.RegisterEmployeeRequest;
import com.example.backend.exception.EmailAlreadyRegisteredException;
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

    /*Create an account for company and first company user*/
    @Transactional
    public UUID createCompany(RegisterCompanyRequest req) {
        //checks email uniqueness
        if (isEmailRegistered(req.email())) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }


        UUID companyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        Company company = new Company(companyId.toString(), req.companyName());

        User user = new User(userId.toString(), req.fullName(), companyId.toString());

        String hashedPassword = passwordEncoder.encode(req.password());

        Account account = new Account(accountId.toString(), req.email(), hashedPassword, "validated",
                userId.toString());

        companyRepository.saveCompany(company);
        userRepository.saveUser(user);
        accountRepository.saveAccount(account);
        roleRepository.saveRole(roleId.toString(), "ADMIN", userId.toString());

        return companyId;
    }

    @Transactional
    public UUID createEmployee(RegisterEmployeeRequest req) {
        //checks email uniqueness
        if (isEmailRegistered(req.email())) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }
        String companyId = req.companyId();// taken from req for an exisiting company
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User user = new User(userId.toString(), req.fullName(), companyId);

        String hashedPassword = passwordEncoder.encode(req.password());

        Account account = new Account(accountId.toString(), req.email(), hashedPassword,"Validated",
                userId.toString());

        userRepository.saveUser(user);
        accountRepository.saveAccount(account);
        roleRepository.saveRole(roleId.toString(), "EMPLOYEE", userId.toString());

        return userId;
    }

    private boolean isEmailRegistered(String email) {
        return accountRepository.findAccountByEmail(email).isPresent();
    }

}