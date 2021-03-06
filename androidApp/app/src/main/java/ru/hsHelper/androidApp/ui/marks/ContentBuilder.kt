package ru.hsHelper.androidApp.ui.marks

import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.data.Path
import ru.hsHelper.androidApp.rest.codegen.models.UserWork

object ContentBuilder {
    fun groupTable(groupWorks: List<UserWork>): MarksTableContent.Group {
        val courseRows = groupWorks
            .groupBy { it.work.coursePart.course.id }
            .values
            .map(::courseRow)
        return MarksTableContent.Group(courseRows)
    }

    fun courseTable(courseWorks: List<UserWork>): MarksTableContent.Course {
        val coursePartRows = courseWorks
            .groupBy { it.work.coursePart.id }
            .values
            .map(::coursePartRow)
        return MarksTableContent.Course(coursePartRows)
    }

    fun coursePartTable(coursePartWorks: List<UserWork>): MarksTableContent.CoursePart {
        val workRows = coursePartWorks.asSequence()
            .sortedBy { it.work.deadline }
            .map(::workRow)
            .toList()
        return MarksTableContent.CoursePart(workRows)
    }

    private fun workRow(userWork: UserWork): MarksRowContent.Work {
        return MarksRowContent.Work(
            userWork.work.name,
            userWork.work.deadline,
            ButtonData("Assignment") {},
            userWork.sendTime,
            ButtonData("Solution") {},
            userWork.mark,
            userWork.work.weight
        )
    }

    private fun coursePartRow(userWorks: List<UserWork>): MarksRowContent.CoursePart {
        val coursePart = userWorks[0].work.coursePart
        val name = coursePart.name
        val path = Path.CoursePart(coursePart.id)
        val launcher = ButtonData(name, MarksActivity.launcher(name, path))
        val weight = coursePart.weight
        val mark = coursePartMark(userWorks)
        return MarksRowContent.CoursePart(launcher, mark, weight)
    }

    private fun courseRow(userWorks: List<UserWork>): MarksRowContent.Course {
        val course = userWorks[0].work.coursePart.course
        val name = course.name
        val path = Path.Course(course.id)
        val launcher = ButtonData(name, MarksActivity.launcher(name, path))
        val mark = courseMark(userWorks)
        return MarksRowContent.Course(launcher, mark)
    }

    private fun weighedUserWorkMark(work: UserWork): MarkInterval {
        val mark = work.mark
        return if (mark == null) {
            MarkInterval(10.0, 10.0, 0.0) * work.work.weight
        } else {
            MarkInterval(mark * work.work.weight)
        }
    }

    fun coursePartMark(works: List<UserWork>): MarkInterval {
        if (works.isEmpty()) {
            return MarkInterval(10.0, 10.0, 0.0)
        }
        return works.asSequence()
            .map(::weighedUserWorkMark)
            .reduce(MarkInterval::plus)
    }

    private fun weighedCoursePartMark(works: List<UserWork>): MarkInterval {
        return coursePartMark(works) * works[0].work.coursePart.weight
    }

    fun courseMark(works: List<UserWork>): MarkInterval {
        if (works.isEmpty()) {
            return MarkInterval(10.0, 10.0, 0.0)
        }
        return works
            .groupBy { it.work.coursePart.id }
            .values.asSequence()
            .map(::weighedCoursePartMark)
            .reduce(MarkInterval::plus)
    }
}