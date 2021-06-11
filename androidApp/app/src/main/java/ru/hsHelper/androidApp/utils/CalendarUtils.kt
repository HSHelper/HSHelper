package ru.hsHelper.androidApp.utils

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun AppCompatActivity.calendarAddDefaultEvent() {
    withPermissions(arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)) {
        val calID: Long = 1
        val startMillis: Long = Calendar.getInstance().run {
            set(2021, 9, 14, 7, 30)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(2021, 9, 14, 8, 45)
            timeInMillis
        }

        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, "Jazzercise")
            put(CalendarContract.Events.DESCRIPTION, "Group workout")
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
        }
        val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        val eventID: Long? = uri?.lastPathSegment?.toLong()

        if (uri == null) {
            Log.i("Calendar", "uri == null")
        } else if (eventID == null ) {
            Log.i("Calendar", "eventId == null")
        }else {
            Log.i("Calendar", "event added")
        }

    }
}

fun AppCompatActivity.withPermissions(permissions: Array<String>, action: () -> Unit) {
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (!it.values.contains(false)) {
                Log.i("Calendar", "got permission")
                action()
            } else {
                Log.i("Calendar", "no permission")
            }
        }

    var isGranted = true
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            isGranted = false
        }
    }

    if (isGranted) {
        Log.i("Calendar", "got permission")
        action()
    } else {
        Log.i("Calendar", "ask permission")
        requestPermissionLauncher.launch(permissions)
    }
}