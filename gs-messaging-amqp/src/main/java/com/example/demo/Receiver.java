package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@AllArgsConstructor
@Getter
public class Receiver {
    private CountDownLatch latch;

    public void receiveMessage(String message) {
        log.info("Message received <{}>", message);
        latch.countDown();
    }

}
