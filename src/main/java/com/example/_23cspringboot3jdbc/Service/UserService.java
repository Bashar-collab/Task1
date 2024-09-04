package com.example._23cspringboot3jdbc.Service;

import com.example._23cspringboot3jdbc.Validation.DataValidation;
import com.example._23cspringboot3jdbc.models.User;
import com.example._23cspringboot3jdbc.models.UserDto;
import com.example._23cspringboot3jdbc.repository.UserRepository;
import com.example._23cspringboot3jdbc.response.MessageResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
//import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    // Method to handle user signup
    public ResponseEntity<?> signup(@Valid User user)
    {

        logger.info("Attempting to sign up user with email: " + user.getEmail() + " and username " + user.getUsername());
        ResponseEntity<?> validationResponse = DataValidation.validateNewUser(user);

        if (validationResponse != null) {
            logger.warn("Validation failed for user with email: " + user.getEmail());
            return validationResponse;
        }
        // Check if the user with the provided email already exists
        Optional <User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent())
        {
            logger.error("Error: Email " + user.getEmail() + " is already taken!");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
            // throw new RuntimeException("User already exists with email: " + user.getEmail());
        }

        // Encrypt the password before saving (using a simple example, you should use a stronger encryption in real applications)
         String encryptedPassword = passwordEncoder.encode(user.getPassword());
         user.setPassword(encryptedPassword);
         // user.setPassword(user.getPassword());
        // Set the registration date
        user.setRegisterDate(LocalDate.now());

        logger.info("Saving new user with email: " + user.getEmail());
        System.out.println(LocalDateTime.now() + " A record has been added");
        logger.info("User with email: " +  user.getEmail() + " registered successfully at " + user.getRegisterDate());
        // Save the user to the database
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
    private boolean isValidEmail(String email) {
        // You can implement a more sophisticated email validation logic here
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    // Handle login

    public ResponseEntity<?> login(String email, String password) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Verify Password
                if (passwordEncoder.matches(password, user.getPassword())) {
                    UserDto userDto = convertToDto(user); // Convert User to UserDto
                    // Return the user details along with a success message
                    logger.info("Login Success!");
                    return ResponseEntity.ok().body(new MessageResponse("Login Successful"));
                } else {
                    // Return a 401 status with an appropriate error message
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new MessageResponse("Invalid password"));
                }
            } else {
                // Return a 404 status with an appropriate error message
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("User not found with email!"));
            }
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("An unexpected error occurred."));
        }
    }



    // Retrieve all users
    public List<UserDto> getAllUsers()
    {
        logger.info("Retrieving all users");
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRegisterDate(user.getRegisterDate());
        return userDto;
    }

    // Method to find a user by username
    public ResponseEntity<?> findByUsername(String username) {
        logger.info("Fetching user with username: " + username);
        List<User> users = userRepository.findByUsernameContaining(username);
        if (!username.isEmpty())
        {
            logger.info("Username: " + username + " is fetched");
            return ResponseEntity.ok(users);
        }
        else
        {
            logger.warn("User with username: " + username + " not found.");
            return ResponseEntity.badRequest().body(new MessageResponse("No user is found"));
        }
    }

    // Endpoint to find a user by register date
    public ResponseEntity<?> findByRegisterDate(String registerDate)
    {
        try {
            logger.info("Fetching user with registration date: " + registerDate);

            LocalDate registerDateT = LocalDate.parse(registerDate);
            List<User> users = userRepository.findByRegisterDate(registerDateT);

            if (!users.isEmpty()) {
                logger.info("User with registration date: " + registerDateT + " is fetched");
                return ResponseEntity.ok(users);
            } else {
                logger.warn("User with registration date: " + registerDateT + " not found.");
                return ResponseEntity.badRequest().body(new MessageResponse("No user is found"));
            }
        } catch (Exception e) {
            logger.error("Error parsing date or fetching users", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid date format or an error occurred"));
        }
    }
    // Find users registered between two dates
    public ResponseEntity<?> getUsersRegisteredBetween(String start, String end) {
        try {
            logger.info("Retrieving users between the two dates below:");
            logger.info("Start Date: " + start);
            logger.info("End Date: " + end);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(start, formatter);
            LocalDate endDate = LocalDate.parse(end, formatter);

            List<User> users = userRepository.findAllByRegisterDateBetween(startDate, endDate);
            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("No users found between the given dates"));
            }
        } catch (DateTimeParseException e) {
            logger.error("Error parsing dates", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid date format. Please use yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("An unexpected error occurred"));
        }
    }


    //    public List<User> g
    // Delete one row by id
    public void deleteUserById(Long id)
    {
        logger.info("Deleting user with id: " + id);
        userRepository.deleteById(id);
        logger.info("User with id: " + id + " has been deleted ");
    }

    // Delete many rows based on register date
//    deleteUsers
    @Transactional
    public ResponseEntity<?> deleteUsersByRegisterDate(String start, String end)
    {
        try {
            logger.info("Deleting users between two dates below:");
            logger.info("Start Date: " + start);
            logger.info("End Date: " + end);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(start, formatter);
            LocalDate endDate = LocalDate.parse(end, formatter);

            // Delete users within the specified date range
            userRepository.deleteAllByRegisterDateBetween(startDate, endDate);

            logger.info("Users registered between " + startDate + " and " + endDate + " have been deleted");
            return ResponseEntity.ok(new MessageResponse("Users successfully deleted"));
        } catch (DateTimeParseException e) {
            logger.error("Error parsing dates", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid date format. Please use yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("An unexpected error occurred while deleting users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("An unexpected error occurred"));
        }
    }

    // Update one row by id
    public ResponseEntity<?> updateUserById(Long id, User newUserDetails)
    {
        logger.info("Updating user with id " + id + " details!" );
        // Check if the email already exists in the database
        Optional<User> existingUserWithEmail = userRepository.findByEmail(newUserDetails.getEmail());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
            logger.warn("Email already exists: " + newUserDetails.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        ResponseEntity<?> validationResponse = DataValidation.validateUpdatedUser(newUserDetails);

        if (validationResponse != null) {
            logger.warn("Validation failed for user with email: " + newUserDetails.getEmail());
            return validationResponse;
        }
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
        {
            User user = optionalUser.get();
            user.setUsername(newUserDetails.getUsername());
//            user.setLastName(newUserDetails.getLastName());
            user.setEmail(newUserDetails.getEmail());
//            user.setPassword(newUserDetails.getPassword());
//            user.setRegisterDate(newUserDetails.getRegisterDate());
            logger.info("User with id: " + id + " has been updated");
            // save new data to database
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("New Data is saved successfully!"));
        }
        else
        {
            logger.error("User not found with id: " + id);
            throw new RuntimeException("User not found with id: " + id);
        }

    }

    // Update many rows based on register date
    /*
    public List<User> updateUsersByRegisterDate(LocalDate startDate, LocalDate endDate, User newUserDetails)
    {
        logger.info("Updating users registered between " + startDate + " and " +  endDate);
        List<User> users = userRepository.findAllByRegisterDateBetween(startDate, endDate);
        for (User user : users)
        {
            user.setUsername(newUserDetails.getUsername());
//            user.setLastName(newUserDetails.getLastName());
            user.setEmail(newUserDetails.getEmail());
            user.setPassword(newUserDetails.getPassword());
            user.setRegisterDate(newUserDetails.getRegisterDate());
        }
        logger.info("Users registered between " + startDate + " and " + endDate  + " have been updated");
        return userRepository.saveAll(users);
    }
*/

}
