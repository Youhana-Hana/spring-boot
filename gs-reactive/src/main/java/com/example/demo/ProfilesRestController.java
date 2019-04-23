package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

//@RestController
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfilesRestController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    Flux<Profile> getAll() {
        return profileService.all();
    }

    @GetMapping("/{id}")
    Mono<Profile> getById(@PathVariable("id") String id) {
        return profileService.get(id);
    }

    @PostMapping
    Mono<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        return profileService.create(profile.getEmail())
                .map(p -> ResponseEntity.created(URI.create("/profiles/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .build());
    }

    @DeleteMapping("/{id}")
    Mono<Profile> deleteById(@PathVariable("id") String id) {
        return profileService.delete(id);
    }


    @PutMapping("/{id}")
    Mono<ResponseEntity<Profile>> updateById(@PathVariable("id") String id, @RequestBody Profile profile) {
        return profileService.update(id, profile.getEmail())
                .map(p -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build());
    }
}
