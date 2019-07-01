package com.example.demo.ui;

import com.example.demo.application.ApplyForCardService;
import com.example.demo.model.CreditCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class CreditCardApplicationControllerTest {

    @Mock
    private ApplyForCardService applyForCardService;

    @InjectMocks
    private CreditCardApplicationController creditCardApplicationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(creditCardApplicationController).build();
    }

    @Test
    void rejectedCard_when_before_seventeen() throws Exception {
        doReturn(Optional.empty()).when(this.applyForCardService).apply(anyString());

        mockMvc.perform(post("/applications").content(objectMapper.writeValueAsString(new CardApplication("3000")))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void acceptedCard_when_before_seventeen() throws Exception {
        doReturn(Optional.of(new CreditCard())).when(this.applyForCardService).apply(anyString());

        mockMvc.perform(post("/applications").content(objectMapper.writeValueAsString(new CardApplication("3000")))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
 }