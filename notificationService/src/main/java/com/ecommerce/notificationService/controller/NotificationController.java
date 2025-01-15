package com.ecommerce.notificationService.controller;

import com.ecommerce.notificationService.model.Notification;
import com.ecommerce.notificationService.service.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final KafkaProducerService kafkaProducerService;
    public NotificationController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
        try {
            String message = objectMapper.writeValueAsString(notification);
            kafkaProducerService.sendMessage("notifications", message);
            return ResponseEntity.ok("Notification queued for processing.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to queue notification.");
        }
    }
}
