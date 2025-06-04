package com.dauphine.jobCompass.services.Notification;

import com.dauphine.jobCompass.dto.Notification.NotificationDto;
import com.dauphine.jobCompass.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto notificationDto) throws NotFoundException;
    List<NotificationDto> getAllNotificationsByUserId(UUID userId);
    NotificationDto updateNotificationReadStatus(UUID notificationId, boolean isRead) throws NotFoundException;
    List<NotificationDto> getUnreadNotificationsByUserId(UUID userId);

}