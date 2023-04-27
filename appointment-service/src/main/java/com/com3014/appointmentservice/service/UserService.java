package com.com3014.appointmentservice.service;

import com.com3014.appointmentservice.model.json.JsonUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Value("${user-auth-service-uri}")
    private String AUTH_SERVICE_BASE_URI;

    private final RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<JsonUser> getAllDoctors() {
        String url = "%s/api/users/doctors".formatted(AUTH_SERVICE_BASE_URI);
        return List.of(Objects.requireNonNull(restTemplate.getForObject(url, JsonUser[].class)));
    }
}
