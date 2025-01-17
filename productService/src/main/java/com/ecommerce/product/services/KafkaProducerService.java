package com.ecommerce.product.services;

import com.ecommerce.product.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, ProductDto> productKafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, ProductDto> productKafkaTemplate) {
        this.productKafkaTemplate = productKafkaTemplate;
    }

    public void sendMessageCU(String topic, ProductDto productDto) {
        System.out.println("Sending message to Kafka Topic: " + topic);
        try {
            CompletableFuture<SendResult<String, ProductDto>> future =
                    productKafkaTemplate.send(topic, productDto);
            future.handleAsync((result, ex) -> {
                if (ex != null) {
                    System.err.println("Failed to send message to topic: " + topic +
                            ", Error: " + ex.getMessage());
                } else {
                    System.out.println("Message sent successfully to topic: " + topic);
                }
                return result;
            });
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    public void sendMessageD(String topic, ProductDto productDto) {
        productDto.setOperation("DELETE");
        sendMessageCU(topic, productDto);
    }
}