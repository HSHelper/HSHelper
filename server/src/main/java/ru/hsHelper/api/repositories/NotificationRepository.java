package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hsHelper.api.entities.Notification;

import java.util.Set;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    Set<Notification> findAllByIdIn(Set<Long> notificationsIds);

    Notification findByNotificationType(Notification.NotificationType notificationType);
    @NotNull
    Set<Notification> findAll();
}
