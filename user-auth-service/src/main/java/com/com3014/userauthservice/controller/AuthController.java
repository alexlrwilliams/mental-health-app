package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.service.JwtService;
import com.com3014.userauthservice.model.json.JsonAuth;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.model.json.TokenValidationRequest;
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
    @Autowired
    public AuthController(UserService userService,
                          JwtService jwtService,
                          UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JsonUser jsonUser) {
        var user = userService.createUser(jsonUser);
        var refreshToken = jwtService.generateToken(user);
        var accessToken = jwtService.generateToken(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(user.getUsername())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(accessToken);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody JsonAuth jsonAuth) {
        return ResponseEntity.ok(userService.authenticateCredentials(jsonAuth));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody TokenValidationRequest tokenValidationRequest) {
        var userDetails = userDetailsService.loadUserByUsername(tokenValidationRequest.getEmail());
        return ResponseEntity.ok(jwtService.validateToken(
                tokenValidationRequest.getToken(),
                userDetails
                ));
    }
}
