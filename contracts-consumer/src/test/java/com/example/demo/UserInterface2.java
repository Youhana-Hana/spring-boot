package com.example.demo;

@FunctionalInterface
public interface UserInterface2<T, R, X extends Throwable> {
    R apply(T instance) throws X;
}
