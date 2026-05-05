package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.example.backend.dao.UserRepository;
import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.service.LoginService;
import com.example.backend.service.RegisterService;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.UserChangePassword;
import com.example.backend.utils.PasswordManager;

import java.util.*;

@RequestMapping("api/auth")
@RestController
public class UserController {

    private final RegisterService registerService;
    private final LoginService loginService;

    public UserController(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping("/company")
    public ResponseEntity<?> createCompany(@RequestBody RegisterRequest req) {
        UUID companyId = registerService.createCompany(req);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "Company created successfully",
                "success", true,
                "data", companyId));
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@RequestBody RegisterRequest req) {
        UUID userId = registerService.createEmployee(req);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "User Account created successfully",
                "success", true,
                "data", userId));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {

        Account validAccount = loginService.loginUser(account.getEmail(), account.getPassword());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "Successfully logged in",
                "success", true,
                "data", validAccount));
    }

    /*
     * @GetMapping("/{id}")
     * public Optional<User> findById(@PathVariable String id) {
     * return userRepository.findById(id);
     * }
     * 
     * @GetMapping
     * public List<User> findAll() {
     * return userRepository.findAll();
     * }
     * 
     * @GetMapping("/search")
     * public List<User> searchUsers(
     * 
     * @RequestParam(required = false) String email,
     * 
     * @RequestParam(required = false) Boolean isActive) {
     * return userRepository.search(email, isActive);
     * }
     * 
     * @PutMapping("/{id}")
     * public String update(@PathVariable String id, @RequestBody User user) {
     * return userRepository.update(id, user);
     * }
     * 
     * @DeleteMapping("/{id}")
     * public String delete(@PathVariable String id) {
     * return userRepository.delete(id);
     * }
     * 
     * @PutMapping("/change-password")
     * public String changePassword(@RequestBody UserChangePassword ucp) {
     * return userRepository.changePassword(ucp);
     * }
     */
}
