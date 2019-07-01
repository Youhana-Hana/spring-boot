package com.example.demo.ui;

import com.example.demo.application.ApplyForCardService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController("/applications")
class CreditCardApplicationController {
    private final ApplyForCardService applyForCardService;

    @Autowired
    CreditCardApplicationController(ApplyForCardService applyForCardService) {
        this.applyForCardService = applyForCardService;
    }

    @PostMapping
    ResponseEntity applyForCard(@RequestBody CardApplication cardApplication) {
        System.out.println(cardApplication.getPesel());

        return applyForCardService
                .apply(cardApplication.getPesel())
                .map(card -> ok().build())
                .orElse(status(HttpStatus.FORBIDDEN).build());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CardApplication {
    @JsonProperty
    private String pesel;
}