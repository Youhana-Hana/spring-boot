package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    @Override
    public void run(String... args) throws Exception {
      log.info("Sending message");
      rabbitTemplate.convertAndSend("spring-bbot-exchange", "foo.bar.baz", "Hello from RabbitMQ!");
      receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
