package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Links;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.core.WebHandler.linkTo;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    @ResponseBody
    public Greeting getGreeting(@RequestParam(name ="name", defaultValue = "Stranger", required = false) String name) {

        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        Stream<Object> build = Stream.builder()
                .add(greeting)
                .build();

        build.map(g -> new EntityModel(g, linkTo(methodOn(Controller.class).getGreeting()).withSelfRel()))

        return greeting;
    }
}
