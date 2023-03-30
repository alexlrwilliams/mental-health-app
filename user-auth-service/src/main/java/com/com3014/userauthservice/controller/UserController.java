package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody JsonUser jsonUser) {
        return userService.createUser(jsonUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserByIdOrThrow(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id,
                           @RequestBody JsonUser jsonUser) {
        return userService.updateUser(id, jsonUser);
    }
}
