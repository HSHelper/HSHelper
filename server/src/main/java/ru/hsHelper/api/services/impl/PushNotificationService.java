package ru.hsHelper.api.services.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hsHelper.api.configs.PushNotifyConf;
import ru.hsHelper.api.entities.User;

import java.util.Set;

@Service
public class PushNotificationService {
    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public PushNotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotification(PushNotifyConf note, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getTitle())
                .setBody(note.getBody())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();

        return firebaseMessaging.send(message);
    }

    public void processNotifications(PushNotifyConf note, Set<User> users, ru.hsHelper.api.entities.Notification notification) {
        users.stream().filter(u -> u.getNotifications().contains(notification)).
                forEach(u -> {
                    try {
                        sendNotification(note, u.getFirebaseMessagingToken());
                    } catch (FirebaseMessagingException e) {
                        e.printStackTrace();
                    }
                });
    }
}
