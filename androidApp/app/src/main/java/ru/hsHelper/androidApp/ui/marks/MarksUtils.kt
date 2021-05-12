package ru.hsHelper.androidApp.ui.marks

import ru.hsHelper.androidApp.rest.codegen.models.Course
import ru.hsHelper.androidApp.rest.codegen.models.CoursePart
import ru.hsHelper.androidApp.rest.codegen.models.UserWork


private fun summarizeCoursePart(works: List<UserWork>): Pair<CoursePart, MarkInterval> {
    val weight = works.sumOf { it.work?.weight ?: 0.0 }
    val sum = works.sumOf { (it.work?.weight ?: 0.0) * (it.mark ?: 0.0) }
    return Pair(works[0].work!!.coursePart!!, MarkInterval.calculate(sum, weight))
}

private fun summarizeCourse(
    parts: List<Pair<CoursePart, MarkInterval>>
): Pair<Course, MarkInterval> {
    val sumAverage = parts.sumOf { (it.first.weight!!) * (it.second.average) }
    val sumMax = parts.sumOf { (it.first.weight!!) * (it.second.average) }
    val sumMin = parts.sumOf { (it.first.weight!!) * (it.second.average) }
    return Pair(parts[0].first.course!!, MarkInterval(sumMax, sumAverage, sumMin))
}

internal fun summarizeExpandable(marks: List<ExpandableMark>): MarkInterval {
    val sumAverage = marks.sumOf { (it.weight) * (it.mark.average) }
    val sumMax = marks.sumOf { (it.weight) * (it.mark.max) }
    val sumMin = marks.sumOf { (it.weight) * (it.mark.min) }
    return MarkInterval(sumMax, sumAverage, sumMin)
}

internal fun summarizeWorks(marks: List<WorkMark>): MarkInterval {
    val weight = marks.asSequence()
        .filter { it.mark != null }
        .sumOf { it.weight }
    val sum = marks.asSequence()
        .filter { it.mark != null }
        .sumOf { (it.weight) * (it.mark!!) }
    return MarkInterval.calculate(sum, weight)
}

internal fun getGroupTable(allWorks: List<UserWork>, groupId: Long): List<ExpandableMark> {
    return allWorks.asSequence()
        .filter { it.work?.coursePart?.course?.group?.id == groupId }
        .groupBy { it.work!!.coursePart!!.id }
        .values.asSequence()
        .map(::summarizeCoursePart)
        .groupBy { it.first.course!!.id }
        .values.asSequence()
        .map(::summarizeCourse)
        .map(MarksViewModel::courseMarkRepresentation)
        .toList()
}


internal fun getCourseTable(allWorks: List<UserWork>, courseId: Long): List<ExpandableMark> {
    return allWorks.asSequence()
        .filter { it.work?.coursePart?.course?.id == courseId }
        .groupBy { it.work!!.coursePart!!.id }
        .values.asSequence()
        .map(::summarizeCoursePart)
        .map(MarksViewModel::coursePartMarkRepresentation)
        .toList()
}

internal fun getCoursePartWorksTable(allWorks: List<UserWork>, coursePartId: Long): List<WorkMark> {
    return allWorks.asSequence()
        .filter { it.work!!.coursePart!!.id == coursePartId }
        .sortedBy { it.work?.deadline }
        .map(MarksViewModel::userWorkRepresentation)
        .toList()
}
