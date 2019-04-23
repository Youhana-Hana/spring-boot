package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Component
@Profile("demo")
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        profileRepository
                .deleteAll()
                .thenMany(
                        Flux.just("A", "B", "C", "D")
                                .map(name -> new com.example.demo.Profile(UUID.randomUUID().toString(), name + "@email.com"))
                                .flatMap(profileRepository::save)
                )
                .thenMany(profileRepository.findAll())
                .subscribe(profile -> log.info("Saving " + profile.toString()));
    }
}
