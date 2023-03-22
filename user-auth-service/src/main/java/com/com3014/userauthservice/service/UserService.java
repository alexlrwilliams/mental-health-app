package com.com3014.userauthservice.service;

import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteUser(String email) {
        userRepository.delete(getUserOrThrow(email));
    }

    public User updateUser(String email, JsonUser jsonUser) {
        var user = getUserOrThrow(email)
                .setFirstName(jsonUser.getFirstName())
                .setLastName(jsonUser.getLastName())
                .setUsername(jsonUser.getEmail())
                .setPassword(encryptPassword(jsonUser.getPassword()))
                .setAddress(jsonUser.getAddress())
                .setRole(jsonUser.getRole());
        return userRepository.save(user);
    }


    public User getUserOrThrow(String email) {
        return getUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Could not find user with email '%s'".formatted(email)));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByUsername(email);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
