package com.example._23cspringboot3jdbc.Controller;

import com.example._23cspringboot3jdbc.Requests.LoginRequest;
import com.example._23cspringboot3jdbc.Service.UserService;
import com.example._23cspringboot3jdbc.models.User;
import com.example._23cspringboot3jdbc.models.UserDto;
import com.example._23cspringboot3jdbc.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600) // allows for a specific domains to make request for a limited time
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    // Endpoint to handle user signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        logger.info("Registering this user " + user);
        return userService.signup(user);
    }

    // Endpoint to handle user login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDto userDto = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            logger.info("Login Successful");
            // Return the user details along with a success message
            return ResponseEntity.ok().body(new MessageResponse("Login Successful"));
        } catch (UsernameNotFoundException e) {
            // Return a 404 status with an appropriate error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("User not found with email!"));
        } catch (IllegalArgumentException e) {
            // Return a 401 status with an appropriate error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid password"));
        }
    }

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
        logger.info("Fetching user with username: " + username);

        List<User> users = userService.findByUsername(username);
        if (!users.isEmpty()) {
            logger.info("Username: " + username + " is fetched");
            return ResponseEntity.ok(users);
        } else {
            logger.warn("User with username: " + username + " not found.");
            return ResponseEntity.badRequest().body(new MessageResponse("No user is found"));
        }
    }

    // Endpoint to find a user by register date
    @GetMapping("/{registerDate}")
    public ResponseEntity<?> getUserByRegisterDate(@PathVariable String registerDate) {
        try {
            logger.info("Fetching user with registration date: " + registerDate);

            LocalDate registerDateT = LocalDate.parse(registerDate);

            List<User> users = userService.findByRegisterDate(registerDateT);
            if (!users.isEmpty()) {
                logger.info("User with registration: " + registerDateT + " is fetched");
                return ResponseEntity.ok(users);
            } else {
                logger.warn("User with registration: " + registerDateT + " not found.");
//                return ResponseEntity.notFound().build();
                return ResponseEntity.badRequest().body(new MessageResponse("No user is found"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error parsing date or fetching users", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    // Existing method to find users registered between two dates
    @GetMapping("/registered-between")
    public List<User> getUsersRegisteredBetween(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end)
    {
        logger.info("Retrieving users between two dates below");
        logger.info("Start Date: " + start);
        logger.info("End Date: " + end);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        return userService.getUsersRegisteredBetween(startDate, endDate);
    }

    // Delete one row by id
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id)
    {
        logger.info("User by id " + id + " is deleted");
        userService.deleteUserById(id);
    }

    // Delete many rows based on register date
    @DeleteMapping("/registered-between")
    public void deleteUserByRegisterDate(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end) {
        logger.info("Deleting users between two dates below");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        userService.deleteUsersByRegisterDate(startDate, endDate);
    }

    // Update one row by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody User newUserDetails){
        logger.info("Updating user with id's " + id + " details!" );
        return userService.updateUserById(id, newUserDetails);
    }

    // Update many rows based on register date
    /*
    @PutMapping("/registered-between")
    public List<User> updatedUsersByRegisterDate(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestBody User newUserDetails)
    {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return userService.updateUsersByRegisterDate(startDate, endDate, newUserDetails);
    }

     */
}
