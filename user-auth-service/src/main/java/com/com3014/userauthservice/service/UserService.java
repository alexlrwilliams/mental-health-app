package com.com3014.userauthservice.service;

import com.com3014.userauthservice.exceptions.AuthorityNotFoundException;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return getUser(email);
    }

    public User createUser(JsonUser jsonUser) {
        var user = new User(
                jsonUser.getEmail(),
                jsonUser.getPassword(),
                jsonUser.getAuthorities(),
                jsonUser.getFirstName(),
                jsonUser.getLastName(),
                jsonUser.getAddress()
        );
        return userRepository.save(user);
    }

    public void deleteUser(String email) {
        userRepository.delete(getUser(email));
    }

    public User updateUser(String email, JsonUser jsonUser) {
        var user = getUser(email);
        user.setFirstName(jsonUser.getFirstName());
        user.setLastName(jsonUser.getLastName());
        user.setEmail(jsonUser.getEmail());
        user.setPassword(jsonUser.getPassword());
        user.setAddress(jsonUser.getAddress());
        user.setAuthorities(jsonUser.getAuthorities());
        return userRepository.save(user);
    }

    private User getUser(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new AuthorityNotFoundException(
                        "Could not find authority with email '%s'".formatted(email)));
    }
}
