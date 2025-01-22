package com.ecommerce.userservice.config;

import com.ecommerce.userservice.UserServiceApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
public class EncoderConfig {
    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public Logger getLogger() {
        return Logger.getLogger(UserServiceApplication.class.getName());
    }
}