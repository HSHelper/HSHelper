package ru.hsHelper.androidApp.services

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.AuthUser
import ru.hsHelper.androidApp.auth.getRestUser
import ru.hsHelper.androidApp.notifications.LocalNotificationManager
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.userPartialUpdateRequest


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        fun onSignOut() {
            FirebaseMessaging.getInstance().deleteToken()
            Log.d("Firebase Messaging", "Token deleted")
        }

        fun onSignIn(auth: AuthResult) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "Firebase Messaging",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@addOnCompleteListener
                }
                val token = task.result
                if (token == null) {
                    Log.w("Firebase Messaging", "Fetched FCM registration token is null")
                    return@addOnCompleteListener
                }
                sendRegistrationToServer(auth.user!!, token)
                Log.d("Firebase Messaging", "Token updated")
            }
        }

        private fun sendRegistrationToServer(refreshedToken: String) {
            val user = AuthProvider.currentUser
            if (user != null) {
                sendRegistrationToServer(user, refreshedToken)
            }
        }

        private fun sendRegistrationToServer(user: AuthUser, refreshedToken: String) =
            GlobalScope.launch {
                val restUser = user.getRestUser()
                if (restUser.firebaseMessagingToken != refreshedToken) {
                    val userUpdateRequest = userPartialUpdateRequest(restUser)
                    userUpdateRequest.token = refreshedToken
                    RestProvider.userApi.updateUserUsingPUT(restUser.id, userUpdateRequest)
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
