package com.example._23cspringboot3jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
public class DemoApplication {

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
//        System.out.println("PasswordEncoder bean: " + passwordEncoder);
    }

}
