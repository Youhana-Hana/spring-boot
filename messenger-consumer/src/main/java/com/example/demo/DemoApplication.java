package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableBinding(Sink.class)
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Component
@Slf4j
class Listener {

    @StreamListener(target = Sink.INPUT, condition = "headers['type'] == 'card-granted'")
    public void receiveGranted(@Payload CardGranted granted) {
        log.info("\n\nGRANTED [" + granted.getClientPesel() + "] !!!! :) :) :)\n\n");
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type'] == 'card-application-rejected'")
    public void receiveCardRejected(@Payload CardRejected rejected) {
        log.error("\n\nREJECTED [" + rejected.getClientPesel() + "] !!!! :( :( :(\n\n");
    }
}
