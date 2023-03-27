package com.com3014.apigateway;

import com.com3014.apigateway.model.TokenValidationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {

    @Value("${user-auth-service-uri}")
    private String AUTH_SERVICE_BASE_URI;

    public Mono<Boolean> validateToken(TokenValidationRequest tokenValidationRequest) {
        return WebClient.create(AUTH_SERVICE_BASE_URI)
                .post()
                .uri("/api/auth/validate")
                .bodyValue(tokenValidationRequest)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
