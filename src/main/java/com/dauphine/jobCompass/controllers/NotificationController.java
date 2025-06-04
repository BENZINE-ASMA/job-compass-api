package com.dauphine.jobCompass.controllers;


import com.dauphine.jobCompass.dto.Notification.NotificationDto;
import com.dauphine.jobCompass.services.Notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "API de gestion des notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle notification")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        try {
            NotificationDto createdNotification = notificationService.createNotification(notificationDto);
            return ResponseEntity.ok(createdNotification);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Récupérer toutes les notifications d'un utilisateur")
    public ResponseEntity<List<NotificationDto>> getUserNotifications(@PathVariable UUID userId) {
        List<NotificationDto> notifications = notificationService.getAllNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Récupérer les notifications non lues d'un utilisateur")
    public ResponseEntity<List<NotificationDto>> getUnreadUserNotifications(@PathVariable UUID userId) {
        List<NotificationDto> notifications = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{notificationId}/read-status") // Changé de PUT à PATCH
    @Operation(summary = "Mettre à jour le statut de lecture d'une notification")
    public ResponseEntity<NotificationDto> updateReadStatus(
            @PathVariable UUID notificationId,
            @RequestParam boolean isRead) {
        try {
            System.out.println(notificationId);
            NotificationDto updatedNotification = notificationService.updateNotificationReadStatus(notificationId, isRead);
            return ResponseEntity.ok(updatedNotification);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/mark-all-read/{userId}")
    public ResponseEntity<Integer> markAllNotificationsAsRead(@PathVariable UUID userId) {
        int updatedCount = notificationService.markAllNotificationsAsRead(userId);
        return ResponseEntity.ok(updatedCount);
    }
}