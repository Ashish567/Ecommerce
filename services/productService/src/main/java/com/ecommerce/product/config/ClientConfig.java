package com.ecommerce.product.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bean")
public class ClientConfig {
    private String message = "Message from backend is: %s <br/> Services : %s";
}
