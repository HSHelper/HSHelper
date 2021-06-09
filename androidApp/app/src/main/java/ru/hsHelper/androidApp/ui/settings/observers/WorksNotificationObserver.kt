package ru.hsHelper.androidApp.ui.settings.observers

import androidx.preference.PreferenceFragmentCompat
import ru.hsHelper.R
import ru.hsHelper.androidApp.rest.codegen.models.Notification

class WorksNotificationObserver(preference: PreferenceFragmentCompat) : NotificationsObserver(
    preference,
    R.string.new_homework,
    Notification.NotificationTypeEnum.WORKUPDATE
)