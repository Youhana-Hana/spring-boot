package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(final Person person) throws Exception {
        var transformed = new Person(person.getFirstName().toUpperCase(), person.getLastName().toUpperCase());

        log.info("Converting ({}) into ({})", person, transformed);

        return transformed;
    }
}
