package com.example.demo;

import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;


public class StreamTest {
    private static List<Employee> employees = Arrays.asList(
            new Employee(1, "Jeff Bezos", 100.0),
            new Employee(2, "Bill Gates", 200.0),
            new Employee(3, "Bill Gates", 201.0),
            new Employee(4, "Mark Zuckerberg", 300.0)
    );


    @Test
    public void whenStreamGroupingAndReducing_thenGetMap() {
        Comparator<Employee> byNameLength = Comparator.comparing(Employee::getName);

        Map<Character, Optional<Employee>> longestNameByAlphabet = employees.stream().collect(
                Collectors.groupingBy(e -> e.getName().charAt(0),
                        Collectors.reducing(BinaryOperator.maxBy(comparing(Employee::getSalary)))));

        System.out.println(longestNameByAlphabet);

        assertThat(longestNameByAlphabet.get('B').get().getName()).isEqualTo("Bill Gates");
        assertThat(longestNameByAlphabet.get('J').get().getName()).isEqualTo("Jeff Bezos");
        assertThat(longestNameByAlphabet.get('M').get().getName()).isEqualTo("Mark Zuckerberg");
    }

    @Test
    public void whenStreamReducing_thenGetValue() {
        Double percentage = 10.0;
        Double salIncrOverhead = employees.stream()
                .collect(Collectors.reducing(
                        0.0, e -> e.getSalary() * percentage / 100, (s1, s2) -> s1 + s2));
    }

    @Test
    public void whenIncrementSalaryForEachEmployee_thenApplyNewSalary() {
        employees.stream().forEach(e -> e.salaryIncrement(10.0));

        assertThat(employees.get(0).getSalary()).isEqualTo(110.0);

        assertThat(employees).extracting(Employee::getSalary).contains(110.0, Index.atIndex(0));
        assertThat(employees).extracting(Employee::getSalary).contains(210.0);
        assertThat(employees).extracting(Employee::getSalary).contains(310.0);
    }

    @Test
    public void peek() {
        List<Employee> collect = employees.stream()
                .peek(e -> e.salaryIncrement(10.0))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    @Test
    public void join() {
        String collect1 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(","));

        System.out.println(collect1);
    }

    @Test
    public void collection() {
        DoubleSummaryStatistics collect = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println(collect);
    }

    @Test
    public void sorted() {
        employees.stream()
                .sorted(comparing(Employee::getName))
                .forEach(System.out::println);
    }

    @Test
    public void min() {
        Employee employee = employees.stream()
                .min(comparing(Employee::getId))
                .orElseThrow(NoSuchElementException::new);

        assertThat(employee.getId()).isEqualTo(1);
    }

    @Test
    public void map() {
        List<String> collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        assertThat(collect).contains("Jeff Bezos", "Bill Gates", "Mark Zuckerberg");
    }

    @Test
    public void findfirst() {
        Employee employee = employees.stream()
                .filter(e -> e.getSalary() > 23333)
                .findFirst()
                .orElse(null);

        assertThat(employee).isNull();
    }

    @Test
    public void flatMap() {
        List<List<String>> lists = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Youhana", "Hana")
        );

        List<String> collect = lists.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println(collect);


        String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};

        //Stream<String[]>
        Stream<String[]> temp = Arrays.stream(data);

        temp.flatMap(Arrays::stream).forEach(System.out::println);
    }

    @Test
    public void flatMapSet() {
        Student obj1 = new Student();
        obj1.setName("mkyong");
        obj1.addBook("Java 8 in Action");
        obj1.addBook("Spring Boot in Action");
        obj1.addBook("Effective Java (2nd Edition)");

        Student obj2 = new Student();
        obj2.setName("zilap");
        obj2.addBook("Learning Python, 5th Edition");
        obj2.addBook("Effective Java (2nd Edition)");

        List<Student> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);

        List<String> collect = list.stream()
                .map(Student::getBook)
                .flatMap(Set::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }


    @Test
    public void whenLimitInfiniteStream_thenGetFiniteElements() {
        Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);

        List<Integer> collect = infiniteStream
                .skip(3)
                .limit(5)
                .collect(Collectors.toList());

        assertThat(collect).isEqualTo(Arrays.asList(16, 32, 64, 128, 256));
    }

}
