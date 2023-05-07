package com.com3014.userauthservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {
    @Value("${s3-service-uri}")
    private String S3_SERVICE_BASE_URL;

    @Value("${profile-endpoint}")
    private String PROFILE_PIC_ENDPOINT;

    private final RestTemplate restTemplate;

    @Autowired
    public S3Service() {
        this.restTemplate = new RestTemplate();
    }

    public String saveProfilePicture(MultipartFile profilePicture) {
        String url = "%s/upload".formatted(S3_SERVICE_BASE_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", profilePicture.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        System.out.println(url);
        restTemplate.postForEntity(url, requestEntity, String.class);

        return "%s/%s".formatted(PROFILE_PIC_ENDPOINT,profilePicture.getOriginalFilename());
    }
}
