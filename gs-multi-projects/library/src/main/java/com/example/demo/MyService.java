package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(MyServiceProperties.class)
public class MyService {
    @Autowired
    MyServiceProperties serviceProperties;

    public String message() {
        return this.serviceProperties.getMessage();
    }
}
