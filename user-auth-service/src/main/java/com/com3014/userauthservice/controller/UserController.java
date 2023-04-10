package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @JsonView(User.Views.Public.class)
    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody JsonUser jsonUser) {
        var user = userService.createUser(jsonUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getUsername())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(user);
    }

    @JsonView(User.Views.Public.class)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserByIdOrThrow(id);
    }

    @JsonView(User.Views.Public.class)
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmailOrThrow(email);
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
