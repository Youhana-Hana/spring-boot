package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest
@Import({ProfileEndpointConfiguration.class,
        ProfileHandler.class})
public class ProfileEndpoints {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ProfileService profileService;

    @Test
    void getAll() {
        when(this.profileService.all()).thenReturn(Flux.just(new Profile("1", "A"), new Profile("2", "B")));

        this.webTestClient
                .get()
                .uri("/profiles")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].email").isEqualTo("A")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].email").isEqualTo("B");
    }
}
