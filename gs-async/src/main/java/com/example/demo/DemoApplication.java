package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@EnableAsync
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	public CommandLineRunner runner(GitHubLookupService service) {
		return args -> {

			// Start the clock
			long start = System.currentTimeMillis();

			var user1  = service.findUser("PivotalSoftware");
			var user2  = service.findUser("CloudFoundry");
			var user3  = service.findUser("Spring-Projects");

			CompletableFuture.allOf(user1, user2, user3).join();

			// Print results, including elapsed time
			log.info("Elapsed time: " + (System.currentTimeMillis() - start));
			log.info("--> " + user1.get());
			log.info("--> " + user2.get());
			log.info("--> " + user3.get());
		};
	}
}
