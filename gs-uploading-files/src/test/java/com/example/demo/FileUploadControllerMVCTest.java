package com.example.demo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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