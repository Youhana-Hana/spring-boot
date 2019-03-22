package com.example.demo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FileUploadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileStorage fileStorage;

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    void setup() {
        System.out.println("Before each test method");

        mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController)
                .build();
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all test methods");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Before each test method");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all test methods");
    }

    @Nested
    @DisplayName("LoadAll")
    class LoadAllFiles {
        @BeforeEach
        void beforeEach() {
            System.out.println("Before each test method of the WhenX class");
        }

        @AfterEach
        void afterEach() {
            System.out.println("After each test method of the WhenX class");
        }

        @Test
        void shouldListAllFiles() throws Exception {

            when(fileStorage.loadAll()).thenReturn(Stream.of(Paths.get("1.txt"), Paths.get("2.txt")));

            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("uploadForm"))
                    .andExpect(model().attribute("files", Matchers.contains(Paths.get("1.txt"), Paths.get("2.txt"))));
        }
    }

    @Nested
    @DisplayName("LoadAll2")
    class LoadAllFiles2 {
        @Test
        void shouldListAllFiles() throws Exception {

            when(fileStorage.loadAll()).thenReturn(Stream.of(Paths.get("1.txt"), Paths.get("2.txt")));

            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("uploadForm"))
                    .andExpect(model().attribute("files", Matchers.contains(Paths.get("1.txt"), Paths.get("2.txt"))));
        }

        @ParameterizedTest(name = "Year {0} is a leap year.")
        @ValueSource(ints = { 2016, 2020, 2048 })
        void if_it_is_one_of_the_following_years(int year) {
            System.out.println("Year " + year);
        }
    }

}