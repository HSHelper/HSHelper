package ru.hsHelper.androidApp.calendar

import android.Manifest
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.activity.ComponentActivity
import ru.hsHelper.androidApp.utils.withPermissions

object Calendar {
    fun addEvent(
        activity: ComponentActivity,
        title: String, description: String,
        startMillis: Long, endMillis: Long, timeZone: String = "Europe/Moscow"
    ) {
        activity.withPermissions(
            arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
            )
        ) {
            val calID: Long = getCalendarDefaultId(activity) ?: 4

            val values = ContentValues().apply {
                put(CalendarContract.Events.CALENDAR_ID, calID)
                put(CalendarContract.Events.TITLE, title)
                put(CalendarContract.Events.DESCRIPTION, description)
                put(CalendarContract.Events.DTSTART, startMillis)
                put(CalendarContract.Events.DTEND, endMillis)
                put(CalendarContract.Events.EVENT_TIMEZONE, timeZone)
            }
            val uri: Uri? =
                activity.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
            val eventID: Long? = uri?.lastPathSegment?.toLong()

            if (uri == null) {
                Log.i("Calendar", "uri == null")
            } else if (eventID == null) {
                Log.i("Calendar", "eventId == null")
            } else {
                Log.i("Calendar", "event added: $uri, $eventID")
            }
        }
    }

    private fun getCalendarDefaultId(activity: ComponentActivity): Long? {
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        )

        var calCursor = activity.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )

        if (calCursor != null && calCursor.count <= 0) {
            calCursor = activity.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1",
                null,
                CalendarContract.Calendars._ID + " ASC"
            )
        }

        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                val calName: String
                val calID: String
                val nameCol = calCursor.getColumnIndex(projection[1])
                val idCol = calCursor.getColumnIndex(projection[0])

                calName = calCursor.getString(nameCol)
                calID = calCursor.getString(idCol)

                Log.d("Calendar", "Calendar name = $calName Calendar ID = $calID")

                calCursor.close()
                return calID.toLong()
            }
        }
        return null
    }

    object DEBUG {
        fun showAllCalendars(activity: ComponentActivity) {
            activity.withPermissions(
                arrayOf(
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
                )
            ) {
                val EVENT_PROJECTION: Array<String> = arrayOf(
                    CalendarContract.Calendars._ID,                     // 0
                    CalendarContract.Calendars.NAME,                    // 1
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
                    CalendarContract.Calendars.OWNER_ACCOUNT            // 3
                )

                val PROJECTION_ID_INDEX: Int = 0
                val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
                val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
                val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

                // Run query
                val uri: Uri = CalendarContract.Calendars.CONTENT_URI
                val cur: Cursor? =
                    activity.contentResolver.query(uri, EVENT_PROJECTION, null, null, null)

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

                println("ID = ${getCalendarDefaultId(activity)}")
            }
        }
    }
}
