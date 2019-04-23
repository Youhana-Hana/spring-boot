package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GitHubLookupService {

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        log.info("Looking up {}", user);

        var uri = String.format("https://api.github.com/users/%s", user);
        var results = restTemplate.getForObject(uri, User.class);

        Thread.sleep(1000L);

        return CompletableFuture.completedFuture(results);
    }
}
