package com.com3014.apigateway;

import com.com3014.apigateway.exceptions.InvalidTokenException;
import com.com3014.apigateway.exceptions.MissingHeaderException;
import com.com3014.apigateway.model.TokenValidationRequest;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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

            var tokenValidationRequest = new TokenValidationRequest(emailHeader, jwtToken);

            Mono<Boolean> tokenIsValid = authenticationService.validateToken(tokenValidationRequest);

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
