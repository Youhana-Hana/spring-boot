package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessagesService {

    @HystrixCommand(fallbackMethod = "available")
    public String read() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        String forObject = restTemplate.getForObject("http://localhost:9090/message", String.class);

        return forObject;
    }

    public String available() {
        return "not working";
    }
}
