package com.ecommerce.searchservice.service;

import com.ecommerce.searchservice.dto.ProductDto;
import com.ecommerce.searchservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final ProductRepository productRepository;

    @KafkaListener(
            topics = "${spring.kafka.topic-1}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenerForProductDelete(@Payload ProductDto productDto,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Received delete message from topic {}: {}", topic, productDto);
            productRepository.deleteById(String.valueOf(productDto.getId()));
        } catch (Exception e) {
            log.error("Error processing delete message: {}", e.getMessage(), e);
            throw new KafkaException("Error processing delete message", e);
        }
    }

    @KafkaListener(
            topics = "${spring.kafka.topic-2}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenerForProductCreateUpdate(@Payload ProductDto productDto,
                                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Received create/update message from topic {}: {}", topic, productDto);
            if(productDto.getOperation().equals("DELETE")) {
                productRepository.deleteById(String.valueOf(productDto.getId()));
                return;
            }
            productRepository.save(productDto.toEntity());
        } catch (Exception e) {
            log.error("Error processing create/update message: {}", e.getMessage(), e);
            throw new KafkaException("Error processing create/update message", e);
        }
    }
}