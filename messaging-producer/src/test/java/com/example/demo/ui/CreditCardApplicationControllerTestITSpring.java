package com.example.demo.ui;


import com.example.demo.infrastructure.CreditCardJpaRepository;
import com.example.demo.model.CreditCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.Message;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditCardApplicationControllerTestITSpring {

    @Autowired
    CreditCardJpaRepository creditCardJpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    Source source;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageCollector messageCollector;

    BlockingQueue<Message<?>> events;


    @BeforeEach
    void setup() {
        events = messageCollector.forChannel(source.output());
    }

    @Test
    void rejectedCard_when_before_seventeen() throws Exception {

        ResponseEntity<CardApplication> cardApplicationResponseEntity = testRestTemplate.postForEntity("/applications", new CardApplication("70345678"), CardApplication.class);

        assertThat(cardApplicationResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        CreditCard first = creditCardJpaRepository.findAll().iterator().next();

        assertThat(first.getPesel()).isEqualTo("70345678");
    }

    @Test
    void acceptedCard_when_before_seventeen() throws Exception {

        ResponseEntity<CardApplication> cardApplicationResponseEntity = testRestTemplate.postForEntity("/applications", new CardApplication("60345678"), CardApplication.class);

        assertThat(cardApplicationResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertThat((String) events.poll().getPayload()).contains("card-application-rejected");
    }
}