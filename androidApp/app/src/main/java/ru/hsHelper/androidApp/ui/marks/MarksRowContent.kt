package ru.hsHelper.androidApp.ui.marks

import org.threeten.bp.ZonedDateTime
import ru.hsHelper.androidApp.data.ButtonData

sealed class MarksRowContent {
    data class Course(
        val viewLauncher: ButtonData,
        val mark: MarkInterval
    ) : MarksRowContent()

    data class CoursePart(
        val viewLauncher: ButtonData,
        val mark: MarkInterval,
        val weight: Double
    ) : MarksRowContent()

    data class Work(
        val title: String,
        val deadline: ZonedDateTime,
        val statement: ButtonData,
        val submitTime: ZonedDateTime?,
        val solution: ButtonData,
        val mark: Double?,
        val weight: Double
    ) : MarksRowContent()
}