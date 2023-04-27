package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.ValidationUtils;
import com.com3014.userauthservice.exceptions.UnauthorisedAccessException;
import com.com3014.userauthservice.exceptions.UserNotValidException;
import com.com3014.userauthservice.model.Role;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.user.JsonUpdateUser;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.service.UserService;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
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
    public List<User> getAllUsers(@RequestHeader("X-Role-Header") Role userRole,
                                  @RequestHeader("email") String email) {
        validateUserAccess(userRole, List.of(Role.DOCTOR, Role.ADMIN), null, null,
                "User %s cannot access all user information".formatted(email));
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody JsonCreateUser jsonUser,
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

    @JsonView(User.Views.Public.class)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserByIdOrThrow(id);
    }

    @JsonView(User.Views.Public.class)
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email,
                               @RequestHeader(value = "X-Role-Header", required = false) Role userRole,
                               @RequestHeader(value = "email") String userEmail) {
        validateUserAccess(userRole, List.of(Role.DOCTOR, Role.ADMIN), email, userEmail,
                "User %s cannot access user %s information".formatted(userEmail, email));
        return userService.getUserByEmailOrThrow(email);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id,
                           @Valid @RequestBody JsonUpdateUser jsonUser,
                           BindingResult bindingResult,
                           @RequestHeader("email") String email) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        return userService.updateUser(id, jsonUser, email);
    }

    private void validateUserAccess(Role userRole, List<Role> validRoles, String email, String userEmail, String message) {
        var isUser = email != null && userEmail != null && Objects.equals(email, userEmail);
        var hasRoleAccess = userRole != null && validRoles.contains(userRole);
        if (!isUser && !hasRoleAccess) {
            throw new UnauthorisedAccessException(message);
        }
    }
}
