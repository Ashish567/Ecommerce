package com.ecommerce.searchservice.service;

import com.ecommerce.searchservice.dto.ProductDto;
import com.ecommerce.searchservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private static final String TOPIC = "product-to-search";
    private final ProductRepository productRepository;

    @KafkaListener(topics = TOPIC, groupId = "search-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        try {
            ProductDto productDto = record.value().contains("null") ? null : ProductDto.from(record.value());
            System.out.printf("Received message: %s with key: %s from partition: %d with offset: %d%n",
                    productDto, record.key(), record.partition(), record.offset());

            productRepository.save(productDto.toEntity());
        } catch (Exception e) {
            System.err.printf("Error processing message: %s%n", e.getMessage());
            // Consider implementing a proper error handling strategy:
            // - Send to Dead Letter Queue
            // - Retry with backoff
            // - Alert monitoring system
        }
    }
}