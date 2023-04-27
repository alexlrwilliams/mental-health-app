package com.com3014.userauthservice.service;

import com.com3014.userauthservice.exceptions.UnauthorisedAccessException;
import com.com3014.userauthservice.exceptions.UserAlreadyExistAuthenticationException;
import com.com3014.userauthservice.model.Role;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonAuth;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.model.json.user.JsonUpdateUser;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.model.json.user.JsonUser;
import com.com3014.userauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(JsonCreateUser jsonUser) {
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

    public JsonTokenResponse authenticateCredentials(JsonAuth jsonAuth) {
        var user = getUserByEmailOrThrow(jsonAuth.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jsonAuth.getEmail(),
                        jsonAuth.getPassword()
                )
        );
        return jwtService.generateTokenResponse(user);
    }

    public void deleteUser(UUID id) {
        userRepository.delete(getUserByIdOrThrow(id));
    }

    public User updateUser(UUID id, JsonUpdateUser jsonUser, String email) {
        List<Predicate<User>> filters = List.of(
                (User user) -> !user.getId().equals(id)
        );

        var user = getUserByIdOrThrow(id);

        validateUser(jsonUser, user, email, filters);

        var updatedUser = user
                .setFirstName(jsonUser.getFirstName())
                .setLastName(jsonUser.getLastName())
                .setUsername(jsonUser.getEmail())
                .setAddress(jsonUser.getAddress())
                .setHospital(jsonUser.getHospital());
        return userRepository.save(updatedUser);
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

    public List<User> getAllDoctors() {
        return userRepository.findUsersByRole(Role.DOCTOR);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByUsername(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return getUserByEmailOrThrow(email);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateUser(JsonCreateUser jsonUser) {
        validateUser(jsonUser, null, null, List.of());
    }

    private void validateUser(JsonUser jsonUser, User user, String email, List<Predicate<User>> filters) {
        var filter = filters.stream()
                .reduce(Predicate::and)
                .orElse(x -> true);

        getUserByEmail(jsonUser.getEmail())
                .filter(filter)
                .ifPresent(s -> {
                    throw new UserAlreadyExistAuthenticationException(jsonUser.getEmail()
                            .formatted("User with email %s already exists"));
                    }
                );

        if (email != null && !user.getUsername().equals(email)) {
            throw new UnauthorisedAccessException("User %s unauthorized to edit user %s".formatted(email, jsonUser.getEmail()));
        }
    }
}
