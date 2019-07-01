package com.example.demo.infrastructure;

import com.example.demo.card.DomainEventPublisher;
import com.example.demo.card.model.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
class FromDBDomainEventPublisher implements DomainEventPublisher {

    private final DomainEventStorage domainEventStorage;
    private final ObjectMapper objectMapper;
    private final Source source;

    @Autowired
    public FromDBDomainEventPublisher(DomainEventStorage domainEventStorage, ObjectMapper objectMapper, Source source) {
        this.domainEventStorage = domainEventStorage;
        this.objectMapper = objectMapper;
        this.source = source;
    }

    @Override
    public void publish(DomainEvent domainEvent) {

        try {
            domainEventStorage.save(new StoredDomainEvent(objectMapper.writeValueAsString(domainEvent)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 2000)
    @Transactional
    public void publishExternally() {
        domainEventStorage
                .findAllBySentOrderByTimestampDesc(false)
                .forEach(e -> {
                    source.output().send(new GenericMessage<>(e.getContent()));
                    e.sent();
                });
    }
}
