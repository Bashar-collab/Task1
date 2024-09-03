package com.example._23cspringboot3jdbc.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin((form) -> form
                        .loginPage("/signup")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        http
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/users/**").permitAll() // allow for any request to access sign up amd login page
//                        .requestMatchers("/users/login").permitAll()
                .anyRequest().authenticated() // other requests need to be authenticated
                );
        return http.build();
    }
}
