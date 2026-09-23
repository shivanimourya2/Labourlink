package com.labourlink.controller;

import com.labourlink.dto.ApiResponse;
import com.labourlink.model.Notification;
import com.labourlink.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getNotifications(@PathVariable Long userId) {
        try {
            List<Notification> notifications = notificationService.getUserNotifications(userId);
            long unreadCount = notificationService.getUnreadCount(userId);
            Map<String, Object> data = new HashMap<>();
            data.put("notifications", notifications);
            data.put("unreadCount", unreadCount);
            return ResponseEntity.ok(ApiResponse.ok("Notifications found", data));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{userId}/unread-count")
    public ResponseEntity<ApiResponse> getUnreadCount(@PathVariable Long userId) {
        try {
            long count = notificationService.getUnreadCount(userId);
            return ResponseEntity.ok(ApiResponse.ok("Count retrieved", count));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse> markAsRead(@PathVariable Long notificationId) {
        try {
            notificationService.markAsRead(notificationId);
            return ResponseEntity.ok(ApiResponse.ok("Marked as read"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/read-all/{userId}")
    public ResponseEntity<ApiResponse> markAllRead(@PathVariable Long userId) {
        try {
            notificationService.markAllRead(userId);
            return ResponseEntity.ok(ApiResponse.ok("All marked as read"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
