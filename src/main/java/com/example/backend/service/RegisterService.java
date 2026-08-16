package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dao.*;
import com.example.backend.dto.RegisterCompanyRequest;
import com.example.backend.dto.RegisterEmployeeRequest;
import com.example.backend.exception.EmailAlreadyRegisteredException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.*;
import com.example.backend.utils.PasswordManager;

import java.util.*;

@Service
public class RegisterService {

    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    private PasswordManager passwordManager;

    public RegisterService(CompanyRepository companyRepository, UserRepository userRepository,
            AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
            PasswordManager passwordManager) {

        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordManager = passwordManager;
    }

    /* Create an account for company and first company user */
    @Transactional
    public UUID createCompany(RegisterCompanyRequest req) {
        // checks email uniqueness
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
    public UUID createEmployee(RegisterEmployeeRequest req, AuthenticatedUser loggedUser) {
        // checks email uniqueness
        if (isEmailRegistered(req.email())) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }
        String validUserId = loggedUser.getUserId();
        String validUserRole = loggedUser.getRole();
        // only admin can add employee
        if (!validUserRole.equalsIgnoreCase("ADMIN"))
            throw new UnauthorizedAccessException("Only admins can create employees");

        // checks if company exists
        Optional<String> companyId = userRepository.getCompanyId(validUserId);
        if (companyId.isEmpty())
            throw new ResourceNotFoundException("Company not found");

        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User user = new User(userId.toString(), req.fullName(), companyId.get());

        String generatedPassword = passwordManager.generateTemporaryPassword(req.email(), req.fullName());
        String hashedPassword = passwordEncoder.encode(generatedPassword);
        Account account = new Account(accountId.toString(), req.email(), hashedPassword, "PENDING",
                userId.toString());

        userRepository.saveUser(user);
        accountRepository.saveAccount(account);
        roleRepository.saveRole(roleId.toString(), req.role(), userId.toString());
        return userId;
    }

    private boolean isEmailRegistered(String email) {
        return accountRepository.findAccountByEmail(email).isPresent();
    }

}