package ru.hsHelper.androidApp.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.runBlocking
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestUser
import ru.hsHelper.androidApp.notifications.LocalNotificationManager
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.UserUpdateRequest


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        fun updateToken() {
            // TODO: Clear and get new token (after each relogin)
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                if (token == null) {
                    Log.w("Firebase", "Fetched FCM registration token is null", task.exception)
                    return@addOnCompleteListener
                }
                sendRegistrationToServer(token)
                Log.d("Firebase", "Token updated")
            }
        }

        private fun sendRegistrationToServer(refreshedToken: String) = runBlocking {
            val user = AuthProvider.currentUser!!.getRestUser()
            if (user.firebaseMessagingToken != refreshedToken) {
                RestProvider.userApi.updateUserUsingPUT(
                    user.id,
                    UserUpdateRequest(user.email, user.firstName, user.lastName, refreshedToken)
                )
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("Firebase Messaging", "Message received")

        // Send notification if application in foreground
        val title = message.notification?.title
        val content = message.notification?.body
        if (title != null && content != null) {
            LocalNotificationManager.simpleNotification(applicationContext, title, content)
        }
    }

    override fun onNewToken(refreshedToken: String) {
        Log.d("Firebase Messaging", "Refreshed token: $refreshedToken")
        sendRegistrationToServer(refreshedToken)
    }
}
