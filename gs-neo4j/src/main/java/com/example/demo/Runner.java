package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class Runner implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        personRepository.deleteAll();

        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");


        List<Person> team = Arrays.asList(greg, roy, craig);

        greg = personRepository.save(greg);
        roy = personRepository.save(roy);
        craig = personRepository.save(craig);

        greg = personRepository.findByName(greg.getName());
        greg.worskWith(roy);
        greg.worskWith(craig);

        //roy = personRepository.findByName(roy.getName());
        roy.worskWith(craig);
        // We already know that roy works with greg
        personRepository.save(roy);

        // We already know craig works with roy and greg

        log.info("Lookup each person by name...");
        team.stream().forEach(person -> log.info(
                "\t" + personRepository.findByName(person.getName()).toString()));

    }
}
