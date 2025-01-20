package com.ecommerce.config_service.config;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitConfiguration {

    @Value("${spring.cloud.config.server.git.username}")
    private String username;

    @Value("${spring.cloud.config.server.git.password}")
    private String password;

    @Bean
    public CredentialsProvider credentialsProvider() {
        System.out.println("Creating Credentials Provider");
        System.out.println(System.getenv());
        System.out.println(username);
        System.out.println(password);
        return new UsernamePasswordCredentialsProvider(username, password);
    }
}