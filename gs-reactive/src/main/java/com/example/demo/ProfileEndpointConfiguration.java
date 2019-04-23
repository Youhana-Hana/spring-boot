package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> routes(ProfileHandler profileHandler) {
        return route(i(GET("/profiles")), profileHandler::all)
                .andRoute(i(GET("/profiles/{id}")), profileHandler::getById)
                .andRoute(i(DELETE("/profiles/{id}")), profileHandler::deleteById)
                .andRoute(i(POST("/profiles")), profileHandler::create)
                .andRoute(i(PUT("/profiles/{id}")), profileHandler::updateById);

    }

    private RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }

}
