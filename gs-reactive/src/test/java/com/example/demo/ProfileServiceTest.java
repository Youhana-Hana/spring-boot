package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@Import(ProfileService.class)
class ProfileServiceTest {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Test
    void getAll() {

        Flux<Profile> saved = profileRepository.saveAll(Flux.just(new Profile(null, "Josh"), new Profile(null, "John"), new Profile(null, "Matt")));

        Flux<Profile> composite = profileService.all().thenMany(saved);

        Predicate<Profile> match = profile -> saved.any(saveItem -> saveItem.equals(profile)).block();

        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }

    @Test
    void create() {
        Mono<Profile> profileMono = this.profileService.create("email@email.com");

        StepVerifier.create(profileMono)
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .verifyComplete();
    }

    @Test
    void delete() {
        String test = "test";
        Mono<Profile> deleted = this.profileService
                .create(test)
                .flatMap(saved -> this.profileService.delete(saved.getId()));

        StepVerifier
                .create(deleted)
                .expectNextMatches(profile -> profile.getEmail().equalsIgnoreCase(test))
                .verifyComplete();
    }

    @Test
    void delete2() {
        String test = "test";
        Mono<Profile> deleted = this.profileService
                .create(test)
                .flatMap(saved -> this.profileService.delete(saved.getId()));

        Flux<Profile> test1 = this.profileRepository.findByEmail("test");
        StepVerifier
                .create(test1)
                .expectNextCount(0)
                .verifyComplete();

        var profile = test1.blockFirst();
        assertNull(profile);
    }

}