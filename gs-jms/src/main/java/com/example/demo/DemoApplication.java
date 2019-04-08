package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
//@EnableJms
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

//		JmsTemplate bean = run.getBean(JmsTemplate.class);
//		bean.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
	}

}
