package com.example.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.backend.dao.UserRepository;
import com.example.backend.dto.UserResponse;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.AuthenticatedUser;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getEmployeesDetails(AuthenticatedUser validUser) {
        String role = validUser.getRole();
        String userId = validUser.getUserId();

        if (!role.equals("ADMIN"))
            throw new UnauthorizedAccessException("Only admin can get all ids");

        Optional<String> companyId = userRepository.getCompanyId(userId);

        if (companyId.isEmpty())
            throw new ResourceNotFoundException("Company not found");

        return userRepository.fetchEmployeesDetails(companyId.get());
    }
}
