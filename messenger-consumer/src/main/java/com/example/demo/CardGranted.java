package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CardGranted {
    private UUID cardNo;

    private BigDecimal cardLimit;

    private String clientPesel;

    @JsonIgnore
    private Instant timestamp;

}
