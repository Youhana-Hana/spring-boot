package com.example.demo;

import java.util.function.Consumer;

public class FluentMailer {
    private FluentMailer() {};

    private String from;
    private String to;

    public FluentMailer from(final String address) {
        this.from = address;
        return this;
    }

    public FluentMailer to(final String address) {
        this.to = address;
        return this;
    }

    public static void send(final Consumer<FluentMailer> block) {
        FluentMailer mailer = new FluentMailer();
        block.accept(mailer);
        System.out.println("sending...");
    }
}
