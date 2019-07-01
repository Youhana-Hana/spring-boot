package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeStampTaskPropertiesTest {

    @Test
    void testSet() {
        TimeStampTaskProperties properties = new TimeStampTaskProperties();
        properties.setForamt("xx");
        assertThat("xx").isEqualTo(properties.getForamt());
    }
}