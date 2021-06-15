package ru.hsHelper.api.notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import ru.hsHelper.api.entities.User

class PushNotification(
    private val firebase: FirebaseMessaging,
    title: String?,
    body: String?,
    private val data: Map<String, String>
) {
    private val notification = Notification
        .builder()
        .setTitle(title)
        .setBody(body)
        .build()

    fun send(user: User) {
        val message = Message
            .builder()
            .setToken(user.firebaseMessagingToken)
            .setNotification(notification)
            .putAllData(data)
            .build()
        firebase.send(message)
    }
}
