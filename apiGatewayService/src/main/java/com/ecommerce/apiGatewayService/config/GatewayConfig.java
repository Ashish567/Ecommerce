package com.ecommerce.apiGatewayService.config;

import com.ecommerce.apiGatewayService.filter.AuthFilter;
import com.ecommerce.apiGatewayService.filter.PostGlobalFilter;
import com.ecommerce.apiGatewayService.filter.RequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebFilter;

@Configuration
public class GatewayConfig {

    @Autowired
    RequestFilter requestFilter;

    @Autowired
    AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .route("product-service", r -> r
                        .path("/products/**")
                        .uri("lb://product-service"))
                .route("user-service", r -> r
                        .path("/users/**")
                        .uri("lb://user-service"))
                .route("order-service", r -> r
                        .path("/orders/**")
                        .uri("lb://order-service"))
                .build();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebFilter responseFilter() {
        return new PostGlobalFilter();
    }
}
