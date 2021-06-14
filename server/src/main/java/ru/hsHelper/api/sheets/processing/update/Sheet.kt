package ru.hsHelper.api.sheets.processing.update

class Sheet(private val link: SheetLink, private val marksColumns: List<MarksColumn>) : java.io.Serializable {
    fun update(): List<MarkUpdate> {
        val ranges = marksColumns.map { it.range }
        val data = link.loadColumns(ranges).map { column ->
            column.asSequence().map { it.replace(',', '.') }.map { it.toDoubleOrNull() }.toList()
        }
        return marksColumns.zip(data).flatMap { it.first.updateState(it.second) }
    }
}