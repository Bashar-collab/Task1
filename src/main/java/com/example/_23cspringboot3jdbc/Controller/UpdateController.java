package com.example._23cspringboot3jdbc.Controller;

import com.example._23cspringboot3jdbc.Service.UserService;
import com.example._23cspringboot3jdbc.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600) // allows for a specific domains to make request for a limited time
@RestController
@RequestMapping("/users")
public class UpdateController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    // Update one row by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody User newUserDetails){
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
