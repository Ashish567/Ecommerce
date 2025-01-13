package com.ecommerce.orderService.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderEventsTopic() {
        return new NewTopic("order-events", 1, (short) 1);
    }
}
