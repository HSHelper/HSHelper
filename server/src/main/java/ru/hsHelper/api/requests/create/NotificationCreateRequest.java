package ru.hsHelper.api.requests.create;

import ru.hsHelper.api.entities.Notification;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class NotificationCreateRequest implements Serializable {

    @NotNull
    private Notification.NotificationType notificationType;

    public NotificationCreateRequest(@NotNull Notification.NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Notification.NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Notification.NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
