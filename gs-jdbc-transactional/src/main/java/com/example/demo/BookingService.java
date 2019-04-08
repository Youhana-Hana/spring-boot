package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BookingService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void book(String...persons) {
        for(String person : persons) {
      log.info("Booking {} in a seat...", person);
            jdbcTemplate.update("INSERT INTO BOOKING (FIRST_NAME) VALUES(?)", person);
        }
    }

    public List<String> findBooking() {
return jdbcTemplate.query("select ID, FIRST_NAME from BOOKING",
        (rs,rn) -> rs.getString("FIRST_NAME"));
    }
}
