package com.example.demo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class FileUploadControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorage fileStorage;

    @Nested
    @DisplayName("LoadAll")
    class LoadAllFiles {

        @Test
        void shouldListAllFiles() throws Exception {

            when(fileStorage.loadAll()).thenReturn(Stream.of(Paths.get("1.txt"), Paths.get("2.txt")));

            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("uploadForm"))
                    .andExpect(model().attribute("files", Matchers.contains(Paths.get("1.txt"), Paths.get("2.txt"))));
        }
    }
}