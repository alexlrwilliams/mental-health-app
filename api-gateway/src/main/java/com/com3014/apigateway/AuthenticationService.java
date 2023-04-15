package com.com3014.apigateway;

import com.com3014.apigateway.model.Role;
import com.com3014.apigateway.model.TokenValidationRequest;
import com.com3014.apigateway.model.json.JsonUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Value("${user-auth-service-uri}")
    private String AUTH_SERVICE_BASE_URI;

    private final RestTemplate restTemplate;

    @Autowired
    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean validateToken(TokenValidationRequest tokenValidationRequest) {
        String url = "%s/api/auth/validate".formatted(AUTH_SERVICE_BASE_URI);
        HttpEntity<TokenValidationRequest> request = new HttpEntity<>(tokenValidationRequest);
        return Boolean.TRUE.equals(restTemplate
                .postForObject(url, request, Boolean.class));
    }

    public Role getUserRole(String email) {
        String url = "%s/api/users/email/%s".formatted(AUTH_SERVICE_BASE_URI, email);

        HttpHeaders headers = new HttpHeaders();
        headers.set("email", email);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<JsonUser> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, JsonUser.class);

        return Objects.requireNonNull(response.getBody()).getRole();
    }
}