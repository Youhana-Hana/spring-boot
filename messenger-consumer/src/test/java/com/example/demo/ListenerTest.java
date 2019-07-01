package com.example.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ListenerTest {
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private Sink sink;

    @Test
    public void cardGranted() {
        sink.input().send(MessageBuilder.withPayload(
                CardGranted.builder().cardNo(UUID.randomUUID())
                        .cardLimit(BigDecimal.valueOf(3000))
                        .clientPesel("707363").build())
                .setHeader("type", "card-granted")
                .build()
        );

        assertThat(outputCapture.toString()).contains("GRANTED");
    }

    @Test
    public void declined() {
        sink.input().send(MessageBuilder.withPayload(
                CardRejected.builder().clientPesel("client").build())
                .setHeader("type", "card-application-rejected").build());

        assertThat(outputCapture.toString()).contains("REJECTED");
    }

}