package com.ecommerce.orderService.services;

import com.ecommerce.orderService.events.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "order-events";

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("Published event: " + event);
    }
}
