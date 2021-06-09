package ru.hsHelper.androidApp.ui.settings.observers

import androidx.annotation.StringRes
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import kotlinx.coroutines.runBlocking
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestId
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.Notification

abstract class NotificationsObserver(
    preference: PreferenceFragmentCompat,
    @StringRes settingsId: Int,
    private val type: Notification.NotificationTypeEnum
) : SettingsObserver() {
    private val newMark = find<SwitchPreferenceCompat>(preference, settingsId)

    init {
        runBlocking {
            val id = AuthProvider.currentUser!!.getRestId()
            val notifications = RestProvider.userApi.getAllNotificationsUsingGET1(id)
            val notification = notifications.find { it.notificationType == type }
            newMark.isChecked = notification != null
            newMark.setOnPreferenceChangeListener(::markChanged)
        }
    }

    override suspend fun send() {
        val newVal = newMark.isChecked
        val id = (type.ordinal + 1).toLong()
        //TODO: RestProvider.notificationApi.getNotificationByNotificationTypeUsingGET(type.value).id
        val userId = AuthProvider.currentUser!!.getRestId()
        if (newVal) {
            RestProvider.userApi.addNotificationsUsingPUT(listOf(id), userId)
        } else {
            RestProvider.userApi.deleteNotificationsUsingPOST(listOf(id), userId)
        }
    }
}
