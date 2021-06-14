package ru.hsHelper.api.sheets.processing.update

class ColumnRange(private val column: String, private val begin: Int, private val end: Int) {
    val range get() = "$column$begin:$column$end"
    val size get() = end - begin
}