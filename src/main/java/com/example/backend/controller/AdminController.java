package com.example.backend.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.Employee;
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.service.AdminService;

@RequestMapping("api/admin")
@RestController
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees(Authentication authentication) {

        AuthenticatedUser validUser = (AuthenticatedUser) authentication.getPrincipal();

        List<Employee> employees = adminService.viewAllEmployees(validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "employees", employees));
    }
}
