package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("messages")
public class Controller {

	@Autowired
    RestTemplate restTemplate;

@GetMapping("{id}")
	public String getMessage(@PathVariable("id") Long id) {
	Person person = restTemplate.getForObject("http://localhost:8000/persons/{id}", Person.class, id);
	return "Hello" + person.getName() + "-" + person.getSirName();
}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
