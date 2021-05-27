package ru.hsHelper.api.services.impl.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.repositories.NotificationRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.requests.create.NotificationCreateRequest;
import ru.hsHelper.api.services.NotificationService;

import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public Notification createNotification(NotificationCreateRequest notificationCreateRequest) {
        return notificationRepository.save(new Notification(notificationCreateRequest.getNotificationType()));
    }

    @Transactional(readOnly = true)
    @Override
    public Notification getNotificationById(long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No notification with such id")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Notification getNotificationByNotificationType(Notification.NotificationType notificationType) {
        return notificationRepository.findByNotificationType(notificationType);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteNotification(long id) {
        Notification notification = getNotificationById(id);
        for (User user : notification.getUsers()) {
            user.removeNotification(notification);
        }
        notificationRepository.delete(notification);
    }

    @Transactional
    @Override
    public Set<User> getAllUsers(long id) {
        return getNotificationById(id).getUsers();
    }
}
