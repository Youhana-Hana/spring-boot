package com.example.demo.infrastructure;

import com.example.demo.model.CreditCard;
import com.example.demo.model.CreditCardRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface CreditCardJpaRepository extends CrudRepository<CreditCard, UUID>, CreditCardRepository {
}
