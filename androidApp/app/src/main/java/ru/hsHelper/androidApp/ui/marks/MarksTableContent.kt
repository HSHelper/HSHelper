package ru.hsHelper.androidApp.ui.marks

import org.threeten.bp.ZonedDateTime
import ru.hsHelper.androidApp.data.ButtonData

data class ExpandableMark(
    val subGroup: ButtonData,
    val mark: MarkInterval,
    val weight: Double
)

data class WorkMark(
    val title: String,
    val deadline: ZonedDateTime,
    val statement: ButtonData,
    val submitTime: ZonedDateTime?,
    val solution: ButtonData,
    val mark: Double?,
    val weight: Double
)

sealed class MarksTableContent {
    data class ExpandableContent(val content: List<ExpandableMark>) : MarksTableContent()
    data class WorkContent(val content: List<WorkMark>) : MarksTableContent()
}
