package com.example.demo;

import net.bytebuddy.build.Plugin;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.test.junit.rabbit.RabbitTestSupport;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BatchEventsApplicationTests {

    @ClassRule
    public static RabbitTestSupport rabbitTestSupport = new RabbitTestSupport();

    static CountDownLatch countDownLatch = new CountDownLatch(2);

    @Test
    void testExecution() throws InterruptedException {
        SpringApplication.run(JobExecutionListenerBinding.class, "--spring.main.web-environment=false");

        SpringApplication.run(DemoApplication.class, "--server.port=0",
                "--spring.cloud.stream.bindings.output.producer.requiredGroups=testgroup",
                "--spring.jmx.default-domain=fakedomain",
                "--spring.main.webEnvironment=false");
        assertThat(countDownLatch.await(60, TimeUnit.SECONDS))
                .as("The latch did not count down to zero before timeout").isTrue();

    }

    @EnableBinding(Sink.class)
    @Configuration
    @PropertySource("classpath:io/spring/task/listener/job-listener-sink-channel.properties")
    public static class JobExecutionListenerBinding {

        @StreamListener(Sink.INPUT)
        public void receive(JobExecutionEvent execution) {
            System.out.println(execution.getJobExecution().getJobInstance().getJobName());
            assertThat(execution.getJobExecution().getJobInstance().getJobName())
                    .isEqualTo("job2").as("Job name should be job");
                    countDownLatch.countDown();
        }

    }
}
