package ru.hsHelper.androidApp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.hsHelper.R
import ru.hsHelper.androidApp.ui.initial.InitialActivity

object LocalNotificationManager {
    private const val DEFAULT_CHANNEL_ID = "Default"
    private var id = 0

    fun simpleNotification(context: Context, title: String, text: String) {
        val intent = Intent(context, InitialActivity::class.java)
        intent.putExtra("notification", "Simple notification")

        val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.logo_foreground)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            )
            .setWhen(System.currentTimeMillis())
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannelIfNeeded(notificationManager)
        notificationManager.notify(id++, notification)
    }

    private fun createChannelIfNeeded(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    DEFAULT_CHANNEL_ID,
                    "Default channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}