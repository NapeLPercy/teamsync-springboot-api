package com.example.backend.controller;

import java.time.Duration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.model.User;
import com.example.backend.service.LoginService;
import com.example.backend.service.RegisterService;
import com.example.backend.service.UserService;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterEmployeeRequest;
import com.example.backend.dto.RegisterCompanyRequest;
import com.example.backend.dto.UserChangePassword;
import com.example.backend.dto.UserResponse;
import com.example.backend.utils.PasswordManager;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

@RequestMapping("api/auth")
@RestController
public class UserController {

        private final RegisterService registerService;
        private final LoginService loginService;
        private final UserService userService;

        public UserController(RegisterService registerService, LoginService loginService, UserService userService) {
                this.registerService = registerService;
                this.loginService = loginService;
                this.userService = userService;
        }

        // get all users
        @GetMapping("/employees")
        public ResponseEntity<?> findAllEmployeesDetails(Authentication authentication) {
                AuthenticatedUser validUser = (AuthenticatedUser) authentication.getPrincipal();
                List<UserResponse> allEmployeesDetails = userService.getEmployeesDetails(validUser);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                                "success", true,
                                "employees", allEmployeesDetails));
        }

        /* add a company */
        @PostMapping("/company")
        public ResponseEntity<?> createCompany(@RequestBody RegisterCompanyRequest req) {

                UUID companyId = registerService.createCompany(req);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                                "message", "Company created successfully",
                                "success", true,
                                "companyId", companyId));
        }

        /* add an employee to a company */
        @PostMapping("/employee")
        public ResponseEntity<?> createEmployee(
                        Authentication authentication,
                        @RequestBody RegisterEmployeeRequest req) {

                AuthenticatedUser loggedUser = (AuthenticatedUser) authentication.getPrincipal();

                UUID employeeId = registerService.createEmployee(req, loggedUser);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                                "message", "User Account created successfully",
                                "success", true,
                                "data", employeeId));
        }

        // login users
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody Account account, HttpServletResponse response) {
                LoginRequest validAccount = loginService.loginUser(
                                account.getEmail(),
                                account.getPassword());

                ResponseCookie cookie = ResponseCookie.from(
                                "accessToken",
                                validAccount.token())
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/")
                                .maxAge(Duration.ofHours(24))
                                .build();

                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                                "message", "Successfully logged in",
                                "success", true,
                                "user", Map.of(
                                                "email", validAccount.email(),
                                                "role", validAccount.role(),
                                                "userId", validAccount.userId())));
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
