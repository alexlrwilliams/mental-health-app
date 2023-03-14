package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{email}")
    public User getAuthorityTypeByName(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/{email}")
    public void deleteAuthorityTypeByName(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PutMapping("/{email}")
    public User updateAuthorityType(@PathVariable String email,
                                    @RequestBody JsonUser jsonUser) {
        return userService.updateUser(email, jsonUser);
    }
}
