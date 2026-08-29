package com.example.backend.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.backend.dao.CompanyRepository;
import com.example.backend.dao.UserRepository;
import com.example.backend.dto.Employee;
import com.example.backend.dto.UserResponse;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.AuthenticatedUser;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Employee> viewAllEmployees(AuthenticatedUser validUser) {

        String role = validUser.getRole();
        String userId = validUser.getUserId();

        if (!role.equals("ADMIN"))
            throw new UnauthorizedAccessException("Only admin can manage employees");

        Optional<String> companyId = userRepository.getCompanyId(userId);
        if (companyId.isEmpty())
            throw new ResourceNotFoundException("Company doesn't exist");

        return userRepository.getAllEmployees(companyId.get());
    }

    public void removeEmployee(AuthenticatedUser validUser, String employeeId) {
        String role = validUser.getRole();
        String userId = validUser.getUserId();

        if (!role.equals("ADMIN"))
            throw new UnauthorizedAccessException("Only admin can delete employee");

        // find admin company
        Optional<String> companyId = userRepository.getCompanyId(userId);
        if (companyId.isEmpty())
            throw new ResourceNotFoundException("Company doesn't exist");

        // use company to find
        boolean doesUserBelongToCompany = userRepository.userBelongsToCompany(userId, companyId.get());
        if (!doesUserBelongToCompany)
            throw new ResourceNotFoundException("User doesn't belong to company");

        userRepository.deleteEmployee(employeeId);
    }
}
