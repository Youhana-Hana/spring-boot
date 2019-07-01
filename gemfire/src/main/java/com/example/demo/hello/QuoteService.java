package com.example.demo.hello;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class QuoteService {
    protected static final String ID_BASED_QUOTE_URL = "http://gturnquist-quoters.cfapps.io/api/{id}";

    protected static final String RANDOM_QUOTE_BASED_URL = "http://gturnquist-quoters.cfapps.io/api/random";

    private volatile boolean cacheMiss = false;

    private final RestTemplate quoteServiceTemplate = new RestTemplate();

    public boolean isCacheMiss() {
        boolean cacheMiss = this.cacheMiss;
        this.cacheMiss = false;
        return cacheMiss;
    }

    protected void setCacheMiss() {
        this.cacheMiss = true;
    }


    @Cacheable("Quotes")
    public Quote requestQuote(long id) {
        setCacheMiss();
        return requestQuote(ID_BASED_QUOTE_URL, Collections.singletonMap("id", id));
    }


    @CachePut(cacheNames = "Quotes", key="#result.id")
    public Quote requestRandomQuote() {
        setCacheMiss();
        return requestQuote(RANDOM_QUOTE_BASED_URL, Collections.emptyMap());
    }

    private Quote requestQuote(String url, Map<String, Object> urlVariables) {
        return Optional.ofNullable(this.quoteServiceTemplate.getForObject(url, QuoteResponse.class, urlVariables))
                .map(QuoteResponse::getQuote)
                .orElse(null);
    }
}
