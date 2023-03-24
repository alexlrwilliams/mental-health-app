package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.model.BlacklistedToken;
import com.com3014.userauthservice.model.json.JsonAuth;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.model.json.TokenValidationRequest;
import com.com3014.userauthservice.repository.RedisTokenRepository;
import com.com3014.userauthservice.service.JwtService;
import com.com3014.userauthservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserDetailsService userDetailsService;

    private final RedisTokenRepository redisTokenRepository;

    @Autowired
    public AuthController(UserService userService,
                          JwtService jwtService,
                          UserDetailsService userDetailsService,
                          RedisTokenRepository redisTokenRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.redisTokenRepository = redisTokenRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<JsonTokenResponse> register(@RequestBody JsonUser jsonUser) {
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
    public ResponseEntity<JsonTokenResponse> login(@RequestBody JsonAuth jsonAuth) {
        return ResponseEntity.ok(userService.authenticateCredentials(jsonAuth));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody TokenValidationRequest tokenValidationRequest) {
        var userDetails = userDetailsService.loadUserByUsername(tokenValidationRequest.getEmail());
        return ResponseEntity.ok(jwtService.validateAccessToken(
                tokenValidationRequest.getToken(),
                userDetails
                ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JsonTokenResponse> refresh(@RequestBody TokenValidationRequest tokenValidationRequest) {
        var userDetails = userDetailsService.loadUserByUsername(tokenValidationRequest.getEmail());
        return ResponseEntity.ok(jwtService.refreshAccessToken(
                tokenValidationRequest.getToken(),
                userDetails
        ));
    }

    @PostMapping("/logout")
    public BlacklistedToken logout(@RequestBody TokenValidationRequest tokenValidationRequest) {
        String tokenId = jwtService.extractClaim(
                tokenValidationRequest.getToken(),
                claims -> (String) claims.get("jti")
        );
        return redisTokenRepository.save(
                new BlacklistedToken(tokenId)
        );
    }
}
