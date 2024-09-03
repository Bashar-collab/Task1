package com.example._23cspringboot3jdbc.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
firstname
lastname
password
email
registerDate
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email")
        })
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
//        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
//        @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters")
        private String username;

        /*
        @NotBlank
        @Size(max = 20)
        private String lastName;
        */
        @NotBlank // Field must not be NULL or Empty
        @Size(max = 50)
        @Email
        @Column(unique = true)
        private String email;

        @NotBlank
        @Size(min = 6, max = 120)
//        @JsonIgnore
        private String password;

        private LocalDate registerDate;

        public User()
        {

        }

        public User(String username, String email, String password)
        {
//                this.firstName = firstName;
                this.username = username;
                this.email = email;
                this.password = password;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
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

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public LocalDate getRegisterDate() {
                return registerDate;
        }

        public void setRegisterDate(LocalDate registerDate) {
                this.registerDate = registerDate;
        }
}
