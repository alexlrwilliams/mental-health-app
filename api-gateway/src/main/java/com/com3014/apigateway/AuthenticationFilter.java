package com.com3014.apigateway;

import com.com3014.apigateway.exceptions.InvalidTokenException;
import com.com3014.apigateway.exceptions.MissingHeaderException;
import com.com3014.apigateway.model.Role;
import com.com3014.apigateway.model.TokenValidationRequest;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteSecurity routeSecurity;

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(RouteSecurity routeSecurity, AuthenticationService authenticationService) {
        super(Config.class);
        this.routeSecurity = routeSecurity;
        this.authenticationService = authenticationService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!routeSecurity.needsAuth.test(exchange.getRequest())) {
                return chain.filter(exchange);
            }

            String authHeader = getHeaderOrThrow(exchange, HttpHeaders.AUTHORIZATION, "Missing JWT Token in Authorisation header");
            String emailHeader = getHeaderOrThrow(exchange, "email", "Email Header missing");
            String jwtToken = authHeader.split(" ")[1].trim();

            var tokenValidationRequest = new TokenValidationRequest(emailHeader, jwtToken);
            boolean tokenIsValid = authenticationService.validateToken(tokenValidationRequest);

            if (!tokenIsValid) {
                throw new InvalidTokenException("Token %s is not valid for email %s".formatted(jwtToken, emailHeader));
            }

            Role userRole = authenticationService.getUserRole(emailHeader);
            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-Role-Header", userRole.name())
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private String getHeaderOrThrow(ServerWebExchange exchange, String headerName, String errorMessage) {
        return Optional.ofNullable(
                        exchange.getRequest()
                                .getHeaders()
                                .getFirst(headerName)
                )
                .orElseThrow(() -> new MissingHeaderException(errorMessage));
    }

    public static class Config {

    }
}
