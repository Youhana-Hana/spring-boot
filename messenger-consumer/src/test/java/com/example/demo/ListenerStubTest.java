package com.example.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
ids="com.example:message-producer")
public class ListenerStubTest {
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    StubTrigger stubTrigger;

    @Test
    public void cardGranted() {
        stubTrigger.trigger("card_granted");

        assertThat(outputCapture.toString()).contains("GRANTED");
    }

    @Test
    public void declined() {

        assertThat(outputCapture.toString()).contains("REJECTED");
    }

}