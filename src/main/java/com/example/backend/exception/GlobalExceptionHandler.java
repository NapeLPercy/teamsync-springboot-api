package com.example.backend.exception;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // email conflicts
        @ExceptionHandler(EmailAlreadyRegisteredException.class)
        public ResponseEntity<?> handleEmailExists(EmailAlreadyRegisteredException ex) {
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(Map.of(
                                                "message", ex.getMessage(),
                                                "success", false));
        }

        // invalid login
        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<?> handleInvalidLogin(InvalidCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                                "message", ex.getMessage(),
                                "success", false));
        }

        // Unauthorized access
        @ExceptionHandler(UnauthorizedAccessException.class)
        public ResponseEntity<?> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                                "message", ex.getMessage(),
                                "success", false));
        }

        // Resource not found
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<?> handleResouceNotFound(ResourceNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                                "message", ex.getMessage(),
                                "success", false));
        }

        // Resource invalid
        @ExceptionHandler(InvalidResourceException.class)
        public ResponseEntity<?> handleInvalidResource(InvalidResourceException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                                "message", ex.getMessage(),
                                "success", false));
        }

        // database error
        @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<?> handleDatabaseError(
                        DataAccessException e) {

                e.printStackTrace();
                return ResponseEntity.status(500).body(
                                Map.of("success", false,
                                                "error", "Database error",
                                                "message", e.getMessage()));
        }
}