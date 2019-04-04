package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private double salary;

    public void salaryIncrement(double increment) {
        salary += increment;
    }
}
