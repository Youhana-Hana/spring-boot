package com.example.demo;

import com.sun.source.tree.AssertTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class AppRunner implements CommandLineRunner {

    @Autowired
    BookingService bookingService;

    @Override
    public void run(String... args) throws Exception {
        bookingService.book("Alice", "Bob", "Carol");
        Assert.isTrue(bookingService.findBooking().size() == 3, "First booking should work with no problem");
        log.info("Alice, Bob and Carol have been booked");

        try {
            bookingService.book("Chris", "SAMUEL");
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
        }


        for (String person : bookingService.findBooking()) {
            log.info("So far, " + person + " is booked.");
        }
        log.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, " +
                "and Chris was rolled back in the same TX");

        Assert.isTrue(bookingService.findBooking().size() == 3,
                "'Samuel' should have triggered a rollback");
    }
}
