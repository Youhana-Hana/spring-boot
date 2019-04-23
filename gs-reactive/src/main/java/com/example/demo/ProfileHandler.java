package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ProfileHandler {

    @Autowired
    ProfileService profileService;


    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(profileService.all(), Profile.class));
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(profileService.get(serverRequest.pathVariable("id")), Profile.class));
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(profileService.delete(serverRequest.pathVariable("id")), Profile.class));
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Flux<Profile> profileFlux = serverRequest.bodyToFlux(Profile.class)
                .flatMap(p -> profileService.create(p.getEmail()));

        return
                Mono.from(profileFlux)
                .flatMap(p -> ServerResponse.created(URI.create("/profiles/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .build());
    }

    public Mono<ServerResponse> updateById(ServerRequest serverRequest) {
        Flux<Profile> id = serverRequest.bodyToFlux(Profile.class)
                .flatMap(p -> profileService.update(serverRequest.pathVariable("id"), p.getEmail()));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(id, Profile.class);

    }
}
