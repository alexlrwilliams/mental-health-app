package com.com3014.userauthservice.repository;

import com.com3014.userauthservice.model.Role;
import com.com3014.userauthservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findUserByUsername(String email);
    List<User> findUsersByRole(Role role);
}
