package com.ecommerce.notificationService.service;

import com.ecommerce.notificationService.model.Notification;
import com.ecommerce.notificationService.model.NotificationStatus;
import com.ecommerce.notificationService.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.core.env.Environment;

@Service(value = "notificationService")
public class NotificationService implements NotificationServiceInterface {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final Environment environment;
    Logger log = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender, Environment environment) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
        this.environment = environment;
    }
    public void sendEmailNotification(Notification notification) {
        log.info("Sending email notification to: {}", notification.getRecipientEmail());
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage());
            messageHelper.setTo(notification.getRecipientEmail());
            messageHelper.setSubject(notification.getSubject());
            messageHelper.setText(notification.getMessage(), true);
            messageHelper.setFrom(environment.getProperty("spring.mail.username"));

            mailSender.send(messageHelper.getMimeMessage());
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(java.time.LocalDateTime.now());

        } catch (MessagingException e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setMessage(e.getMessage());
            notification.setSentAt(java.time.LocalDateTime.now());
            log.error("Failed to send email notification: {}", e.getMessage());
        }

        notificationRepository.save(notification);
        log.info("Email notification sent successfully.");
    }

    public Optional<Notification> findNotificationById(Long id) {
        return notificationRepository.findById(id);
    }
}