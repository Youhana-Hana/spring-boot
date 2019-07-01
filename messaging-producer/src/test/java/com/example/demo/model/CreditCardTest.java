package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CreditCardTest {

    @Test
    void defaultConstructur_when_noArguments() {
        var card = new CreditCard();
        System.out.println(card.getCardNo());
        assertThat(card.getCardNo()).isNotNull();
    }

    @Test
    void PeselAndLimit_when_noArguments() {
        var card = CreditCard.builder().pesel("pesel").
        cardLimit(BigDecimal.TEN)
                .build();
        System.out.println(card.getCardNo());
        assertThat(card.getCardNo()).isNotNull();
    }

}