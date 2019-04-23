package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CustomerRepository repository) {
		return  args -> {

			repository.deleteAll();

			// save a couple of customers
			repository.save(Customer.builder().firstName("Alice").lastName("Smith").build());
			repository.save(Customer.builder().firstName("Bob").lastName("Smith").build());

			// fetch all customers
			System.out.println("Customers found with findAll():");
			System.out.println("-------------------------------");
			for (Customer customer : repository.findAll()) {
				System.out.println(customer);
			}
			System.out.println();

			// fetch an individual customer
			System.out.println("Customer found with findByFirstName('Alice'):");
			System.out.println("--------------------------------");
			System.out.println(repository.findByFirstName("Alice"));

			System.out.println("Customers found with findByLastName('Smith'):");
			System.out.println("--------------------------------");
			for (Customer customer : repository.findByLastName("Smith")) {
				System.out.println(customer);
			}
		};
	}
}
