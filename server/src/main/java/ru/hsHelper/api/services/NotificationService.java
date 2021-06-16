package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.requests.create.NotificationCreateRequest;

import java.util.Set;

public interface NotificationService {
    Notification createNotification(NotificationCreateRequest notificationCreateRequest);
    Notification getNotificationById(long notificationId);
    Notification getNotificationByNotificationType(Notification.NotificationType notificationType);
    Set<Notification> getAllNotifications();
    void deleteNotification(long notificationId);
    Set<User> getAllUsers(long notificationId);
}
