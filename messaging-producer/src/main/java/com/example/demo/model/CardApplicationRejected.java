package com.example.demo.model;

import com.example.demo.card.model.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class CardApplicationRejected implements DomainEvent {
    private String clientPesel;
    final Instant timestamp = Instant.now();

    @Override
    public String getType() {
        return "card-application-rejected";
    }
}
