package com.example.demo.model;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardRepository {
    CreditCard save(CreditCard creditCard);

    Optional<CreditCard> findById(UUID uuid);
}
