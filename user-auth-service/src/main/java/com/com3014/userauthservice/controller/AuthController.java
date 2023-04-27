package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.ValidationUtils;
import com.com3014.userauthservice.exceptions.InvalidTokenException;
import com.com3014.userauthservice.exceptions.UserNotValidException;
import com.com3014.userauthservice.model.BlacklistedToken;
import com.com3014.userauthservice.model.json.JsonAuth;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.model.json.TokenValidationRequest;
import com.com3014.userauthservice.repository.RedisTokenRepository;
import com.com3014.userauthservice.service.JwtService;
import com.com3014.userauthservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final RedisTokenRepository redisTokenRepository;

    @Autowired
    public AuthController(UserService userService,
                          JwtService jwtService,
                          RedisTokenRepository redisTokenRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.redisTokenRepository = redisTokenRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<JsonTokenResponse> register(@Valid @RequestBody JsonCreateUser jsonUser,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        var user = userService.createUser(jsonUser);
        var tokenResponse = jwtService.generateTokenResponse(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JsonTokenResponse> login(@Valid @RequestBody JsonAuth jsonAuth,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        return ResponseEntity.ok(userService.authenticateCredentials(jsonAuth));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@Valid @RequestBody TokenValidationRequest tokenValidationRequest,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }

        var userDetails = userService.loadUserByUsername(tokenValidationRequest.getEmail());
        return ResponseEntity.ok(jwtService.validateAccessToken(
                tokenValidationRequest.getToken(),
                userDetails
                ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JsonTokenResponse> refresh(@Valid @RequestBody TokenValidationRequest tokenValidationRequest,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        var userDetails = userService.loadUserByUsername(tokenValidationRequest.getEmail());
        return ResponseEntity.ok(jwtService.refreshAccessToken(
                tokenValidationRequest.getToken(),
                userDetails
        ));
    }

    @PostMapping("/logout")
    public BlacklistedToken logout(@Valid @RequestBody TokenValidationRequest tokenValidationRequest,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = ValidationUtils.getErrorMessages(bindingResult).toString();
            throw new UserNotValidException(errors);
        }
        var userDetails = userService.loadUserByUsername(tokenValidationRequest.getEmail());
        var valid = jwtService.validateAccessToken(
                tokenValidationRequest.getToken(),
                userDetails
        );
        if (!valid) {
            throw new InvalidTokenException(
                    "Access token invalid for user %s".formatted(userDetails.getUsername())
            );
        }
        String tokenId = jwtService.extractClaim(
                tokenValidationRequest.getToken(),
                claims -> (String) claims.get("jti")
        );
        return redisTokenRepository.save(
                new BlacklistedToken(tokenId)
        );
    }
}
