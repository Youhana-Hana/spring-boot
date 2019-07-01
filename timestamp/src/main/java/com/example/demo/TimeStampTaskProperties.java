package com.example.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Data
public class TimeStampTaskProperties {

    private String foramt = "yyyy-MM-dd HH:mm:ss.SSS";
}
