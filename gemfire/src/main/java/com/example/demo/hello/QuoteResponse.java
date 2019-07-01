package com.example.demo.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class QuoteResponse {

    @JsonProperty("value")
    private Quote quote;

    @JsonProperty("type")
    private String status;
}
