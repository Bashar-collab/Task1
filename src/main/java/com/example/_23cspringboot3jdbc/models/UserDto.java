package com.example._23cspringboot3jdbc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {
    private long id;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$|^(?!.*(select|insert|update|delete|drop|alter|truncate|create|grant|revoke|union|;|--))[a-zA-Z]+$", message = "Username must contain only alphabetic characters")
    private String username;
    @NotBlank // Field must not be NULL or Empty
    @Size(max = 50)
    @Pattern(regexp = "^(?!.*(select|insert|update|delete|drop|alter|truncate|create|grant|revoke|union|;|--))[a-zA-Z]+$")
    private String email;
    private LocalDate registerDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
}
