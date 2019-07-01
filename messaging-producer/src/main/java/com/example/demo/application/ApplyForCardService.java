package com.example.demo.application;

import com.example.demo.card.DomainEventPublisher;
import com.example.demo.model.CardApplicationRejected;
import com.example.demo.model.CardGranted;
import com.example.demo.model.CreditCard;
import com.example.demo.model.CreditCardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ApplyForCardService {

    private final DomainEventPublisher domainEventPublisher;
    private final CreditCardRepository creditCardRepository;

    public ApplyForCardService(DomainEventPublisher domainEventPublisher, CreditCardRepository creditCardRepository) {
        this.domainEventPublisher = domainEventPublisher;
        this.creditCardRepository = creditCardRepository;
    }

    @Transactional
    public Optional<CreditCard> apply(final String pesel) {
        if (bornBeforeSeventies(pesel)) {
            domainEventPublisher.publish(new CardApplicationRejected(pesel));
            return Optional.empty();
        }


        CreditCard card = CreditCard.withDefaultLimit(pesel);
        creditCardRepository.save(card);
        domainEventPublisher.publish(new CardGranted(card.getCardNo(), card.getCardLimit(), pesel));
        return Optional.of(card);
    }

    private boolean bornBeforeSeventies(String pesel) {
        return Long.valueOf(pesel.substring(0, 2)) < 70;
    }
}
