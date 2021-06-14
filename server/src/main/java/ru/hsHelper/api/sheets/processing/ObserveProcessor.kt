package ru.hsHelper.api.sheets.processing

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.Channel

class ObserveProcessor(private val path: String, private val drive: Drive) {
    fun observe(key: Long, sheetId: String) {
        val channel = Channel().apply {
            id = key.toString()
            type = "webhook"
            address = path + key.toString()
        }
        drive.files().watch(sheetId, channel).execute()
    }
}