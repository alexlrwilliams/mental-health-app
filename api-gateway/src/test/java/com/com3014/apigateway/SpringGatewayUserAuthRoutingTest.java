package com.com3014.apigateway;

import com.com3014.apigateway.model.Role;
import com.com3014.apigateway.model.TokenValidationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 5001)
@ActiveProfiles("test")
public class SpringGatewayUserAuthRoutingTest {

    @LocalServerPort
    protected int port = 0;

    protected WebTestClient webClient;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        this.webClient = WebTestClient
                .bindToServer()
                .responseTimeout(Duration.ofSeconds(10))
                .baseUrl("http://localhost:" + port)
                .build();

        stubFor(
                get(urlMatching("/api/users.*")).willReturn(
                        aResponse().withStatus(HttpStatus.OK.value())));
    }

    @Test
    public void users_route__successful() {
        when(authenticationService.validateToken(ArgumentMatchers.any(TokenValidationRequest.class)
        )).thenReturn(true);
        when(authenticationService.getUserRole(anyString())).thenReturn(Role.PATIENT);
        webClient
                .get()
                .uri("/api/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer 123456789")
                .header("email", "alex@gmail.com")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void other_route__unsuccessful() {
        when(authenticationService.validateToken(ArgumentMatchers.any(TokenValidationRequest.class))).thenReturn(true);
        webClient
                .get()
                .uri("/api/appointments/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer 123456789")
                .header("email", "alex@gmail.com")
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}
