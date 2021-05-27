package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.requests.create.NotificationCreateRequest;

import java.util.Set;

public interface NotificationService {
    Notification createNotification(NotificationCreateRequest notificationCreateRequest);
    Notification getNotificationById(long id);
    Notification getNotificationByNotificationType(Notification.NotificationType notificationType);
    Set<Notification> getAllNotifications();
    void deleteNotification(long id);
    Set<User> getAllUsers(long id);
}
