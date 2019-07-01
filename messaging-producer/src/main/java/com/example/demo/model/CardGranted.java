package com.example.demo.model;

import com.example.demo.card.model.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardGranted implements DomainEvent {
    private UUID cardNo;
    private BigDecimal cardLimit;
    private String clientPesel;
    private final Instant timestamp = Instant.now();

    @Override
    public String getType() {
        return "card-granted";
    }
}
