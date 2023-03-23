package com.com3014.userauthservice.service;

import com.com3014.userauthservice.exceptions.UserAlreadyExistAuthenticationException;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(JsonUser jsonUser) {
        validateUser(jsonUser);
        var user = new User(
                jsonUser.getEmail(),
                encryptPassword(jsonUser.getPassword()),
                jsonUser.getRole(),
                jsonUser.getFirstName(),
                jsonUser.getLastName(),
                jsonUser.getAddress()
        );
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.delete(getUserByIdOrThrow(id));
    }

    public User updateUser(UUID id, JsonUser jsonUser) {
        List<Predicate<User>> filters = List.of(
                (User user) -> !user.getId().equals(id)
        );

        validateUser(jsonUser, filters);
        var user = getUserByIdOrThrow(id)
                .setFirstName(jsonUser.getFirstName())
                .setLastName(jsonUser.getLastName())
                .setUsername(jsonUser.getEmail())
                .setPassword(encryptPassword(jsonUser.getPassword()))
                .setAddress(jsonUser.getAddress())
                .setRole(jsonUser.getRole());
        return userRepository.save(user);
    }


    public User getUserByEmailOrThrow(String email) {
        return getUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Could not find user with email '%s'".formatted(email)));
    }

    public User getUserByIdOrThrow(UUID id) {
        return getUserById(id).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Could not find user with id '%s'".formatted(id)));
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByUsername(email);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateUser(JsonUser user) {
        validateUser(user, List.of());
    }

    private void validateUser(JsonUser user, List<Predicate<User>> filters) {
        var filter = filters.stream()
                .reduce(Predicate::and)
                .orElse(x -> true);

        getUserByEmail(user.getEmail())
                .filter(filter)
                .ifPresent(s -> {
                    throw new UserAlreadyExistAuthenticationException(user.getEmail()
                            .formatted("User with email %s already exists"));
                    }
                );
    }
}
