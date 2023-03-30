package com.com3014.userauthservice.repository;

import com.com3014.userauthservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findUserByUsername(String email);
}
