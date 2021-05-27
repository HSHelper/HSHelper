package ru.hsHelper.api.controllers;

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

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/")
    public Notification createNotification(@RequestBody NotificationCreateRequest notificationCreateRequest) {
        return notificationService.createNotification(notificationCreateRequest);
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/permission-types")
    public Notification getNotificaionByNotificationType(@RequestBody Notification.NotificationType notificationType) {
        return notificationService.getNotificationByNotificationType(notificationType);
    }

    @GetMapping("/")
    public Set<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}/users")
    public Set<User> getUsers(@PathVariable long id) {
        return notificationService.getAllUsers(id);
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable long id) {
        notificationService.deleteNotification(id);
    }
}
