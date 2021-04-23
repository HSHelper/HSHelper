package ru.hsHelper.androidApp.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.hsHelper.androidApp.notifications.LocalNotificationManager


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FirebaseMessaging", "Message received")

        // Send notification if application in foreground
        val title = message.notification?.title
        val content = message.notification?.body
        if (title != null && content != null) {
            LocalNotificationManager.simpleNotification(applicationContext, title, content)
        }
    }

    override fun onNewToken(refreshedToken: String) {
        Log.d("FirebaseMessaging", "Refreshed token: $refreshedToken")
        // sendRegistrationToServer(refreshedToken)
    }
}
