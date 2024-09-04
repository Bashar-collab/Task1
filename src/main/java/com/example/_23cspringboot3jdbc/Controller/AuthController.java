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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600) // allows for a specific domains to make request for a limited time
@RestController
@RequestMapping("/users")
public class AuthController {

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
            // Call the service method and let it handle the login logic
//            UserDto userDto = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

}
