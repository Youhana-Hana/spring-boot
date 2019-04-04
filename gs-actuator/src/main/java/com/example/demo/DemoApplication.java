package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
//		SpringApplicatiIon.run(DemoApplication.class, args);

        System.out.println();

        var people = createPeople();
        Stream.builder().build();

        System.out.println(
                people.stream()
                        .filter(person -> person.getAge() > 12)
                        .filter(person -> person.getGender() == Gender.FEMALE)
                        .map(person -> person.getName())
                        .map(name -> name.toUpperCase())
                        .collect(toList()));

        var numbers = createNumbers();
        Integer factor = 2;
        Stream<Integer> integerStream = Arrays.stream(numbers)
                .map(e -> e * factor);

        integerStream.forEach(System.out::println);

        people.stream()
                .filter(person -> person.getAge() > 12)
                .forEach(System.out::println);

        Predicate<Person> male = person -> person.getGender() == Gender.MALE;
        Predicate<Person> adult = person -> person.getAge() > 30;

        System.out.println("xxxxxxxxxxxxxxx");
        people.stream()
                .filter(male.and(adult))
                .forEach(System.out::println);

        System.out.println("ddddddddddddddddddddddddd");

        Integer reduce = people.stream()
                .map(Person::getAge)
                .reduce(0, (total, age) -> total + age);

        int sum = people.stream()
                .mapToInt(Person::getAge)
                .sum();

        System.out.println(reduce);
        System.out.println(sum);

        System.out.println(
                people.stream()
                        .map(Person::getName)
                        .collect(toList()));

        System.out.println("ddddddddddddddddddddddddd");

        System.out.println(
                people.stream()
                        .map(Person::getName)
                        .collect(toSet()));

        System.out.println(
                people.stream()
                        .collect(groupingBy(Person::getName, mapping(Person::getAge, toList())))
        );


        people.stream()
                .sorted(comparing(Person::getAge).thenComparing(Person::getName))
                .forEach(System.out::println);

    }

    private static Integer[] createNumbers() {
        return asList(1, 3, 5, 7).toArray(Integer[]::new);
    }

    private static List<Person> createPeople() {
        return asList(
                new Person("Sara", 20, Gender.FEMALE),
                new Person("Sara", 22, Gender.FEMALE),
                new Person("Bob", 20, Gender.MALE),
                new Person("Paula", 32, Gender.FEMALE),
                new Person("Paul", 32, Gender.MALE),
                new Person("Jack", 2, Gender.MALE),
                new Person("Jack", 72, Gender.MALE),
                new Person("Jill", 12, Gender.FEMALE)
        );
    }

}
