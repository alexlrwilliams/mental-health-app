package com.com3014.apigateway;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class RouteSecurity {

    private static final PathMatcher pathMatcher = new AntPathMatcher();
    private static final Set<String> permittedEndpoints  = Set.of("/api/auth/**");

    public Predicate<ServerHttpRequest> needsAuth =
            request -> permittedEndpoints
                    .stream()
                    .noneMatch(endpoint -> pathMatcher.match(endpoint, request.getURI().getPath()));

}
