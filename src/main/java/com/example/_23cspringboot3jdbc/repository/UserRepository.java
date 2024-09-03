package com.example._23cspringboot3jdbc.repository;


import com.example._23cspringboot3jdbc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


/*
UserRepository interface extends JpaRepository
User: means the type of entity will manage
Long: indicates the primary key of the user entity id of type long
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Override
    List<User> findByUsernameContaining(String username);

    List<User> findByRegisterDate(LocalDate registerDate);

    List<User> findAllByRegisterDateBetween(LocalDate registerDate, LocalDate registerDate2);

    void deleteById(Long id);

    int deleteAllByRegisterDateBetween(LocalDate registerDate, LocalDate registerDate2);

    Optional<User> findByEmail(String email);

    // Retrieve all users
    List<User> findAll();
}
