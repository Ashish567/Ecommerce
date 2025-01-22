package com.ecommerce.notificationService.service;

import com.ecommerce.notificationService.model.Notification;
import com.ecommerce.notificationService.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    public KafkaConsumerService(NotificationRepository notificationRepository, NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void consumeMessage(String message) {
        try {
            Notification notification = objectMapper.readValue(message, Notification.class);
            notificationService.sendEmailNotification(notification);
            notificationRepository.save(notification);
        } catch (Exception e) {
            System.out.println("Error while consuming message: " + e.getMessage());
        }
    }
}

