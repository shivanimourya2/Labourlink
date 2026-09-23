package com.labourlink.service;

import com.labourlink.model.Notification;
import com.labourlink.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification create(Long userId, String message, String type, Long relatedJobId) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        n.setType(type);
        n.setRelatedJobId(relatedJobId);
        n.setReadStatus(false);
        return notificationRepository.save(n);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadStatusOrderByCreatedAtDesc(userId, false);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndReadStatus(userId, false);
    }

    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setReadStatus(true);
            notificationRepository.save(n);
        });
    }

    public void markAllRead(Long userId) {
        List<Notification> unread = notificationRepository
                .findByUserIdAndReadStatusOrderByCreatedAtDesc(userId, false);
        unread.forEach(n -> {
            n.setReadStatus(true);
            notificationRepository.save(n);
        });
    }
}
