package com.ecommerce.apiGatewayService.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .route("user-service", r -> r
                        .path("/users/**")
                        .uri("lb://user-service"))
                .route("order-service", r -> r
                        .path("/orders/**")
                        .uri("lb://order-service"))
                .build();
    }
}
