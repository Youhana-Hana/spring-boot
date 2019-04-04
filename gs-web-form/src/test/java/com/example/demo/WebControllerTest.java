package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest {

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(WebController.class).build();
    }

    MockMvc mockMvc;

    @Test
    public void shouldHasErros() throws Exception {
        mockMvc.perform(post("/").param("name", "youhana")
                .param("age", "2"))
                .andExpect(model().hasErrors());
    }


    @Test
    public void shouldHasNoErros() throws Exception {
        mockMvc.perform(post("/").param("name", "hana")
                .param("age", "20"))
                .andExpect(model().hasNoErrors());
    }
}