package com.example.demo.card;

import com.example.demo.card.model.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent domainEvent);
}
