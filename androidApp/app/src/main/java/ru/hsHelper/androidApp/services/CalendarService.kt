package ru.hsHelper.androidApp.services

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import com.google.api.services.calendar.model.EventReminder
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader


object CalendarService {
    private const val APPLICATION_NAME = "Google Calendar API Java Quickstart"
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_PATH = "CalendarTokens"
    private val SCOPES: List<String> = listOf(CalendarScopes.CALENDAR)
    private const val CREDENTIALS_FILE_PATH = "/CalendarCredentials.json"
    private val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private const val defaultCalendarId = "primary"

    private fun getCredentials(): Credential {
        // Load client secrets.
        val inStream: InputStream =
            CalendarService::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
                ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val clientSecrets: GoogleClientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(inStream))

        // Build flow and trigger user authorization request.
        val flow: GoogleAuthorizationCodeFlow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver: LocalServerReceiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    private val service: Calendar =
        Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
            .setApplicationName(APPLICATION_NAME)
            .build()

    fun addEvent(event: Event, calendarId: String = defaultCalendarId): String? {
        return service.events().insert(calendarId, event).execute().id
    }

    fun addSimpleEvent(
        summary: String,
        description: String,
        start: DateTime,
        end: DateTime,
        attendeeEmails: List<String>
    ): String? {
        val event = Event()

        event.summary = summary
        event.description = description
        event.start = EventDateTime()
            .setDateTime(start)
            .setTimeZone("Europe/Moscow")
        event.end = EventDateTime()
            .setDateTime(end)
            .setTimeZone("Europe/Moscow")
        event.attendees = attendeeEmails.map {
            EventAttendee().setEmail(it)
        }

        val reminderOverrides: Array<EventReminder> = arrayOf(
            EventReminder().setMethod("email").setMinutes(24 * 60),
            EventReminder().setMethod("popup").setMinutes(10)
        )
        val reminders: Event.Reminders = Event.Reminders()
            .setUseDefault(false)
            .setOverrides(listOf(*reminderOverrides))
        event.reminders = reminders

        return addEvent(event)
    }

    fun updateEvent(event: Event, eventId: String, calendarId: String = defaultCalendarId) {
        service.events().update(calendarId, eventId, event).execute()
    }

    fun deleteEvent(eventId: String, calendarId: String = defaultCalendarId) {
        service.events().delete(calendarId, eventId).execute();
    }

    fun getExampleEvent(): Event {
        val event: Event = Event()
            .setSummary("Google I/O 2015")
            .setLocation("800 Howard St., San Francisco, CA 94103")
            .setDescription("A chance to hear more about Google's developer products.")
        val startDateTime: DateTime = DateTime("2015-05-28T09:00:00-07:00")
        val start: EventDateTime = EventDateTime()
            .setDateTime(startDateTime)
            .setTimeZone("America/Los_Angeles")
        event.start = start
        val endDateTime: DateTime = DateTime("2015-05-28T17:00:00-07:00")
        val end: EventDateTime = EventDateTime()
            .setDateTime(endDateTime)
            .setTimeZone("America/Los_Angeles")
        event.end = end
        val recurrence = arrayOf("RRULE:FREQ=DAILY;COUNT=2")
        event.recurrence = listOf(*recurrence)
        val attendees: Array<EventAttendee> = arrayOf(
            EventAttendee().setEmail("lpage@example.com"),
            EventAttendee().setEmail("sbrin@example.com")
        )
        event.attendees = listOf(*attendees)
        val reminderOverrides: Array<EventReminder> = arrayOf(
            EventReminder().setMethod("email").setMinutes(24 * 60),
            EventReminder().setMethod("popup").setMinutes(10)
        )
        val reminders: Event.Reminders = Event.Reminders()
            .setUseDefault(false)
            .setOverrides(listOf(*reminderOverrides))
        event.reminders = reminders
        return event
    }
}
