package com.ecommerce.notificationService.service;

import com.ecommerce.notificationService.model.Notification;

public interface NotificationServiceInterface {
    void sendEmailNotification(Notification notification);
}
