package com.groww.chatbot.repository;

import com.groww.chatbot.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * repository class for User data
 */
public interface UserRepository extends MongoRepository<User, String> {

    // finds a user by email
    Optional<User> findByEmail(String email);

    // checks if a user exists by email
    boolean existsByEmail(String email);

}
