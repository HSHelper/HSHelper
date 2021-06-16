package ru.hsHelper.api.notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
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
        val token = user.firebaseMessagingToken
        if (token != null) {
            val message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(data)
                .build()
            try {
                firebase.send(message)
            } catch (e: FirebaseMessagingException) {
                System.err.println(e.stackTrace)
            }
        }
    }
}
