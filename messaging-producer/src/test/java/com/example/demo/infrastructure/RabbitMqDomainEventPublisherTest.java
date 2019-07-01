package com.example.demo.infrastructure;

import com.example.demo.card.model.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
public class RabbitMqDomainEventPublisherTest {

    @Autowired
    private Source source;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RabbitMqDomainEventPublisher rabbitMqDomainEventPublisher;

    private BlockingQueue<Message<?>> events;

    @BeforeEach
    void setup() {
        events = messageCollector.forChannel(source.output());
    }

    @Test
    void publish() throws IOException {
        TestDomainEvent youhana = new TestDomainEvent("YOUHANA");
        TestDomainEvent youhana2 = new TestDomainEvent("YOUHANA2");
        System.out.println(youhana.getContent());

        rabbitMqDomainEventPublisher.publish(youhana);
        Message<String> poll = (Message<String>) events.poll();
//
//        Message<String> poll = (Message<String>) events.poll();
//        /*assertThat(poll.getHeaders()).containsKey("type");
//        assertThat(poll.getHeaders().get("type")).isEqualTo("TestDomainEvent");
//        var xx = objectMapper.readValue(poll.getPayload(), TestDomainEvent.class);
//       assertThat(xx.getContent()).isEqualTo("content");
//*/
//        Message<TestDomainEvent> expected =
//                MessageBuilder.withPayload(youhana)
//                        .setHeader("type", "TestDomainEvent")
//                        .build();
//
//        TestDomainEvent testDomainEvent = objectMapper.readValue(poll.getPayload(), TestDomainEvent.class);
//        System.out.println(testDomainEvent.getContent());
//        assertThat(testDomainEvent.getContent()).isEqualTo("YOUHANA");

        assertThat(poll.getPayload()).contains("YOUHANA");
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
class TestDomainEvent implements DomainEvent {

    private String content;

    @Override
    public String getType() {
        return "TestDomainEvent";
    }
}
