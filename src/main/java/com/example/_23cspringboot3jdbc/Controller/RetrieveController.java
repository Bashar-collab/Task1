package com.example._23cspringboot3jdbc.Controller;

import com.example._23cspringboot3jdbc.models.User;
import com.example._23cspringboot3jdbc.models.UserDto;
import com.example._23cspringboot3jdbc.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example._23cspringboot3jdbc.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600) // allows for a specific domains to make request for a limited time
@RestController
@RequestMapping("/users")
public class RetrieveController {

    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    // Endpoint to retrieve users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint to find a user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    // Endpoint to find a user by register date
    @GetMapping("/{registerDate}")
    public ResponseEntity<?> getUserByRegisterDate(@PathVariable String registerDate) {
            return userService.findByRegisterDate(registerDate);
    }
    // Existing method to find users registered between two dates
    @GetMapping("/registered-between")
    public ResponseEntity<?> getUsersRegisteredBetween(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end)
    {
        return userService.getUsersRegisteredBetween(start, end);
    }
}
