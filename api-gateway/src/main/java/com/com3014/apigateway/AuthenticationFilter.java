package com.com3014.apigateway;

import com.com3014.apigateway.exceptions.InvalidTokenException;
import com.com3014.apigateway.exceptions.MissingHeaderException;
import com.com3014.apigateway.model.TokenValidationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteSecurity routeSecurity;

    @Value("${user-auth-service-uri}")
    private String AUTH_SERVICE_BASE_URI;

    public AuthenticationFilter(RouteSecurity routeSecurity) {
        super(Config.class);
        this.routeSecurity = routeSecurity;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!routeSecurity.needsAuth.test(exchange.getRequest())) {
                return chain.filter(exchange);
            }

            String authHeader = Optional.ofNullable(
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION)
                    )
                    .orElseThrow(() -> new MissingHeaderException("Missing JWT Token in Authorisation header"));

            String emailHeader = Optional.ofNullable(
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("Email")
                    )
                    .orElseThrow(() -> new MissingHeaderException("Missing Email header"));


            String jwtToken = authHeader.split(" ")[1].trim();

            TokenValidationRequest tokenValidationRequest = new TokenValidationRequest(emailHeader, jwtToken);

            Mono<Boolean> tokenIsValid = WebClient.create(AUTH_SERVICE_BASE_URI)
                    .post()
                    .uri("/api/auth/validate")
                    .bodyValue(tokenValidationRequest)
                    .retrieve()
                    .bodyToMono(Boolean.class);

            return tokenIsValid.flatMap(valid -> {
                if (!valid) {
                    return Mono.error(new InvalidTokenException("Token %s is not valid for email %s".formatted(jwtToken, emailHeader)));
                }
                return chain.filter(exchange);
            });
        };
    }

    public static class Config {

    }
}
