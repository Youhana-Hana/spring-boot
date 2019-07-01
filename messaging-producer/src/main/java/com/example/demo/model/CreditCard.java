package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CreditCard")
public class CreditCard {

    @Id
    private final UUID cardNo = UUID.randomUUID();
    private String pesel;
    private BigDecimal cardLimit;

    public static CreditCard withDefaultLimit(String pesel) {
        return CreditCard.builder().pesel(pesel).cardLimit(BigDecimal.valueOf(2000)).build();
    }
}
