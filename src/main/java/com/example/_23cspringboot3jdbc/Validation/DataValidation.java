package com.example._23cspringboot3jdbc.Validation;

import com.example._23cspringboot3jdbc.Service.UserService;
import com.example._23cspringboot3jdbc.models.User;
import com.example._23cspringboot3jdbc.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
/*
public class DataValidation {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static ResponseEntity<?> validateUser(User user) {
        logger.info("Validating data");
        if (isValidName(user.getUsername())) {
            logger.error("Username is not valid");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Name can only contain alphabetic characters and must be between 3 and 20 characters"));
        }

        if (isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid email format"));
        }

        if (!isValidPassword(user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password must be at least 6 characters long"));
        }

        if (containsSQLInjection(user.getUsername()) || containsSQLInjection(user.getEmail()) || containsSQLInjection(user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid input"));
        }

        return null;
    }

    public static ResponseEntity<?> validateEditUser(User user) {
        if (isValidName(user.getUsername())) {

            return ResponseEntity.badRequest().body(new MessageResponse("Error: Name can only contain alphabetic characters and must be between 3 and 20 characters"));
        }

        if (isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid email format"));
        }

        if (containsSQLInjection(user.getUsername()) || containsSQLInjection(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid input"));
        }

        return null;
    }
    private static boolean isValidName(String name) {
        return name == null || name.matches("^[a-zA-Z]+$") || (name.length() > 3 && name.length() < 20);
    }

//    private static boolean isValidEmail(String email) {
//        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
//    }
    private static boolean isValidEmail(String email) {
        return email == null || email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    private static boolean containsSQLInjection(String input) {
        return input != null && input.contains(";");
    }
}
 */
public class DataValidation {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static ResponseEntity<?> validateNewUser(User user) {
        logger.info("Validating user data");

        if (!isNameValid(user.getUsername())) {
            logger.error("Invalid username: " + user.getUsername());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username must contain only alphabetic characters and be between 3 and 20 characters long"));
        }

        if (!isEmailValid(user.getEmail())) {
            logger.error("Invalid email: " + user.getEmail());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid email format"));
        }

        if (!isPasswordValid(user.getPassword())) {
            logger.error("Invalid password");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password must be at least 6 characters long"));
        }

        if (containsSQLInjection(user.getUsername()) || containsSQLInjection(user.getEmail()) || containsSQLInjection(user.getPassword())) {
            logger.error("SQL Injection detected");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid input detected"));
        }

//        return ResponseEntity.ok(new MessageResponse("Validation passed"));
        return null;
    }

    public static ResponseEntity<?> validateUpdatedUser(User user) {
        logger.info("Validating user data for update");

        if (!isNameValid(user.getUsername())) {
            logger.error("Invalid username: " + user.getUsername());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username must contain only alphabetic characters and be between 3 and 20 characters long"));
        }

        if (!isEmailValid(user.getEmail())) {
            logger.error("Invalid email: " + user.getEmail());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid email format"));
        }

        if (containsSQLInjection(user.getUsername()) || containsSQLInjection(user.getEmail())) {
            logger.error("SQL Injection detected");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid input detected"));
        }

//        return ResponseEntity.ok(new MessageResponse("Validation passed"));
        return null;
    }

    private static boolean isNameValid(String name) {
        return name != null && name.matches("^[a-zA-Z]{3,20}$");
    }

    private static boolean isEmailValid(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    private static boolean containsSQLInjection(String input) {
        return input != null && input.contains(";");
    }
}
