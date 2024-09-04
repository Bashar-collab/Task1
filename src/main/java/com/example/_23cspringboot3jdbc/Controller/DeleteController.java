package com.example._23cspringboot3jdbc.Controller;

import com.example._23cspringboot3jdbc.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "*", maxAge = 3600) // allows for a specific domains to make request for a limited time
@RestController
@RequestMapping("/users")
public class DeleteController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    // Delete one row by id
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id)
    {
        userService.deleteUserById(id);
    }

    // Delete many rows based on register date
    @DeleteMapping("/registered-between")
    public ResponseEntity<?> deleteUserByRegisterDate(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end) {
        return userService.deleteUsersByRegisterDate(start, end);
    }
}
