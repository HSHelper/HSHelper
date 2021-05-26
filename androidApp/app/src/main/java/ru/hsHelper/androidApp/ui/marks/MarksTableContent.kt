package ru.hsHelper.androidApp.ui.marks

sealed class MarksTableContent {
    data class Group(val content: List<MarksRowContent.Course>) : MarksTableContent()
    data class Course(val content: List<MarksRowContent.CoursePart>) : MarksTableContent()
    data class CoursePart(val content: List<MarksRowContent.Work>) : MarksTableContent()
}
