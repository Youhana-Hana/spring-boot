package com.example.demo.infrastructure;

import com.example.demo.card.DomainEventPublisher;
import com.example.demo.card.model.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Primary
@Component
public class RabbitMqDomainEventPublisher implements DomainEventPublisher {

    private final Source source;

    @Autowired
    public RabbitMqDomainEventPublisher(Source source) {
        this.source = source;
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", domainEvent.getType());

        source.output().send(new GenericMessage<>(domainEvent, headers));
    }
}

