package ru.hsHelper.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.requests.create.NotificationCreateRequest;
import ru.hsHelper.api.services.NotificationService;

import java.util.Set;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/")
    public Notification createNotification(@RequestBody NotificationCreateRequest notificationCreateRequest) {
        return notificationService.createNotification(notificationCreateRequest);
    }

    @GetMapping("/{notificationId}")
    public Notification getNotificationById(@PathVariable long notificationId) {
        return notificationService.getNotificationById(notificationId);
    }

    @GetMapping("/permission-types/{notificationType}")
    public Notification getNotificationByNotificationType(@PathVariable Notification.NotificationType notificationType) {
        return notificationService.getNotificationByNotificationType(notificationType);
    }

    @GetMapping("/")
    public Set<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{notificationId}/users")
    public Set<User> getAllUsers(@PathVariable long notificationId) {
        return notificationService.getAllUsers(notificationId);
    }

    @DeleteMapping("/{notificationId}")
    public void deletePermission(@PathVariable long notificationId) {
        notificationService.deleteNotification(notificationId);
    }
}
