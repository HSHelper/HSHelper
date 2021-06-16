package ru.hsHelper.androidApp.integrations

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.net.toUri

object GoogleSheet {
    fun sheetViewAction(sheetId: String, sheetPage: String) = { view: View ->
        val browserIntent = Intent(Intent.ACTION_VIEW, viewLink(sheetId, sheetPage))
        view.context.startActivity(browserIntent)
    }

    private fun viewLink(sheetId: String, sheetPage: String): Uri =
        "https://docs.google.com/spreadsheets/d/$sheetId/edit#gid=$sheetPage".toUri()
}