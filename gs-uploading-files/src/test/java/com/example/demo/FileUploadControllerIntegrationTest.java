package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private FileStorage fileStorage;

    @Autowired
    private FileUploadController fileUploadController;

    @Nested
    @DisplayName("LoadAll")
    class LoadAllFiles {

        @Test
        void shouldListAllFiles() throws Exception {

            when(fileStorage.loadAll()).thenReturn(Stream.of(Paths.get("1.txt"), Paths.get("2.txt")));

            ResponseEntity<String> forEntity = restTemplate.getForEntity("/", String.class);

            assertThat(forEntity.getBody().contains("1.txt")).isTrue();

        }
    }
}