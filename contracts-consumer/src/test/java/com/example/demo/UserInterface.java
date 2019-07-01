package com.example.demo;

@FunctionalInterface
public interface UserInterface<T, x extends Throwable> {
    void accept(T instance) throws x;
}


