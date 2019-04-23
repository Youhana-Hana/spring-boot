package com.example.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    @ResponseBody
    public Greeting getGreeting(@RequestParam(name ="name", defaultValue = "Stranger", required = false) String name) {

        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));

        return greeting;
    }
}
