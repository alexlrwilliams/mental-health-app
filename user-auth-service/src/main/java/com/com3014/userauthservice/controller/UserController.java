package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.ValidationUtils;
import com.com3014.userauthservice.exceptions.UserNotValidException;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody JsonUser jsonUser,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        var user = userService.createUser(jsonUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserByIdOrThrow(id);
    }

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
                           @Valid @RequestBody JsonUser jsonUser,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        return userService.updateUser(id, jsonUser);
    }
}
