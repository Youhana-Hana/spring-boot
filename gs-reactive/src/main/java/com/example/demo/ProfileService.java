package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ApplicationEventPublisher  applicationEventPublisher;

    public Flux<Profile> all() {
        return profileRepository.findAll();
    }

    public Mono<Profile> get(String id) {
        return profileRepository.findById(id);
    }

    public Mono<Profile> update(String id, String email) {
        return profileRepository.findById(id)
                .map(profile -> new Profile(id, email))
                .flatMap(profileRepository::save);
    }

    public Mono<Profile> create(String email) {
        return profileRepository.save(new Profile(null, email))
                .doOnSuccess(profile -> this.applicationEventPublisher.publishEvent(new ProfileCreatedEvent(profile)));
    }

    public Mono<Profile> delete(String id) {
        return profileRepository.findById(id)
                .flatMap(profile -> profileRepository.deleteById(profile.getId()).thenReturn(profile));
    }
}
