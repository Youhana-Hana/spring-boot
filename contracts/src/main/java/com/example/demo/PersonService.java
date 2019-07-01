package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {
    private final Map<Long, Person> persons;

    public PersonService() {
        this.persons = new HashMap<>(5);
        persons.put(1L, new Person(1L, "Richard", "Fin"));
        persons.put(2L, new Person(2L, "Jason", "Ben"));
        persons.put(3L, new Person(3L, "Lolo", "Gin"));
    }

    Person findById(long id) {
        return this.persons.get(id);
    }

    List<Person> getAll() {
        return persons.values().stream().collect(Collectors.toList());
    }
}
