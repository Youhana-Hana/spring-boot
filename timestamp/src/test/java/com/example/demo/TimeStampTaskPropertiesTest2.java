package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties({TimeStampTaskProperties.class})
public class TimeStampTaskPropertiesTest2 {

    @Autowired
    TimeStampTaskProperties properties;

    @Test
    void testSet() {
        properties.setForamt("xx");
        assertThat("xx").isEqualTo(properties.getForamt());
    }
}