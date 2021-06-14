package ru.hsHelper.api.sheets.processing.update

import com.google.api.services.sheets.v4.Sheets

class SheetLink(private val id: String, private val page: String, private val sheets: Sheets) : java.io.Serializable {
    fun loadColumns(ranges: List<ColumnRange>): List<List<String>> {
        val batchGet = sheets.spreadsheets().values().batchGet(id)
        batchGet.ranges = ranges.map { "$page!${it.range}" }
        val responseValues = batchGet.execute().valueRanges.map { it.getValues() }
        val values = ArrayList<List<String>>()
        for (i in ranges.indices) {
            val value = MutableList(ranges[i].size) { "" }
            val responseValue = responseValues[i]
            if (responseValue != null) {
                for (j in responseValue.indices) {
                    value[j] = responseValue[j].getOrElse(0) { "" } as String
                }
            }
            values.add(value)
        }
        return values
    }
}