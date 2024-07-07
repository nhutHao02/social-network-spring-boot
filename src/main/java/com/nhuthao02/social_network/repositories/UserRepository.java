package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
