package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Batcher {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void batch() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
        jdbcTemplate.execute("CREATE TABLE customers (id SERIAL, first_name varchar(255), last_name varchar(255))");

        List<Object[]> names = Arrays.asList("John Woo", "Jeff Dean", "Carl Hana")
                .stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        names.forEach(name -> log.info("Inserting customer with record for {}, {}", name[0], name[1]));

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) values (?, ?)", names);

        log.info("Querying for customer records");

        jdbcTemplate.query("SELECT id, first_name, last_name from customers	where first_name = ?", new Object[]{"Carl"},
                (rs, rn) -> new Customer(rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"))).forEach(customer -> log.info(customer.toString()));
    }
}
