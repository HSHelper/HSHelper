package ru.hsHelper.androidApp.utils

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.icu.util.Calendar
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun AppCompatActivity.calendarAddDefaultEvent() {
    withPermissions(
        arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
    ) {
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
        } else if (eventID == null) {
            Log.i("Calendar", "eventId == null")
        } else {
            Log.i("Calendar", "event added: $uri, $eventID")
        }

        val intent = Intent(Intent.ACTION_VIEW).setData(uri)
        startActivity(intent)
    }
}

fun AppCompatActivity.calendarGetAllCalendars() {
    withPermissions(
        arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
    ) {
        val EVENT_PROJECTION: Array<String> = arrayOf(
            CalendarContract.Calendars._ID,                     // 0
            CalendarContract.Calendars.ACCOUNT_NAME,            // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
            CalendarContract.Calendars.OWNER_ACCOUNT            // 3
        )

        val PROJECTION_ID_INDEX: Int = 0
        val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
        val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
        val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

        // Run query
        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs: Array<String> =
            arrayOf("hshelper.test@gmail.com", "com.gmail", "hshelper.test@gmail.com")
        val cur: Cursor? =
            contentResolver.query(uri, EVENT_PROJECTION,null, null, null)

        if (cur != null) {
            Log.i("Calendar", "iterating:")
            while (cur.moveToNext()) {
                val calID: Long = cur.getLong(PROJECTION_ID_INDEX)
                val displayName: String = cur.getString(PROJECTION_DISPLAY_NAME_INDEX)
                val accountName: String = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)
                val ownerName: String = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX)
                println("$calID | $displayName | $accountName | $ownerName")
            }
        } else {
            Log.i("Calendar", "Cursor == null")
        }
        cur?.close()
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