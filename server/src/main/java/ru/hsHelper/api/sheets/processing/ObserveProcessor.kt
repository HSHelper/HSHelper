package ru.hsHelper.api.sheets.processing

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.Channel
import java.time.Instant

class ObserveProcessor(private val path: String, private val drive: Drive) {
    companion object {
        // Max allowed channel duration time in milliseconds (1 week)
        private const val maxChannelDuration = 604_800_000L
    }

    fun observe(key: Long, sheetId: String) {
        val channel = Channel().apply {
            id = key.toString()
            type = "webhook"
            address = path + key.toString()
            expiration = Instant.now().toEpochMilli() + maxChannelDuration
        }
        drive.files().watch(sheetId, channel).execute()
    }
}