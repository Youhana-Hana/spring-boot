package com.example.demo.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties(ignoreUnknown =  true)
@EqualsAndHashCode(exclude = "quote")
public class Quote {
    private long id;

    private String quote;
}
