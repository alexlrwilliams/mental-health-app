package com.com3014.apigateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 8082)
public class SpringGatewayUserAuthRoutingTest {

    @LocalServerPort
    protected int port = 0;

    protected WebTestClient webClient;

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
    public void contextLoads() {
        webClient
                .get()
                .uri("/api/users")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void contextFails() {
        webClient
                .get()
                .uri("/api/appointments/test")
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}
