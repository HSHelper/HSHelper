package ru.hsHelper.androidApp.ui.marks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestId
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.Course
import ru.hsHelper.androidApp.rest.codegen.models.CoursePart
import ru.hsHelper.androidApp.rest.codegen.models.UserWork

class MarksViewModel : ViewModel() {
    companion object {
        fun courseMarkRepresentation(it: Pair<Course, MarkInterval>) =
            ExpandableMark(
                ButtonData(
                    it.first.name,
                    MarksActivity.launcher(it.first.name, "C${it.first.id}")
                ),
                it.second,
                0.0
            )

        fun coursePartMarkRepresentation(it: Pair<CoursePart, MarkInterval>) =
            ExpandableMark(
                ButtonData(
                    it.first.name,
                    MarksActivity.launcher(it.first.name, "P${it.first.id}")
                ),
                it.second,
                it.first.weight
            )

        fun userWorkRepresentation(userWork: UserWork) =
            WorkMark(
                userWork.work.name,
                userWork.work.deadline,
                ButtonData("Assignment") {},
                userWork.sendTime,
                ButtonData("Solution") {},
                userWork.mark,
                userWork.work.weight
            )
    }

    private val _summary = MutableLiveData<MarkInterval?>()
    val summary: LiveData<MarkInterval?> = _summary

    private val _tableContent = MutableLiveData<MarksTableContent>()
    val tableContent: LiveData<MarksTableContent> = _tableContent

    fun postData(path: String) = GlobalScope.launch {
        val userId = AuthProvider.currentUser!!.getRestId()
        val allWorks = RestProvider.userApi.getAllWorksUsingGET1(userId)
        when (path[0]) {
            'G' -> {
                val tableContent = getGroupTable(allWorks, path.substring(1).toLong())
                _summary.postValue(null)
                _tableContent.postValue(MarksTableContent.ExpandableContent(tableContent))
            }
            'C' -> {
                val tableContent = getCourseTable(allWorks, path.substring(1).toLong())
                _summary.postValue(summarizeExpandable(tableContent))
                _tableContent.postValue(MarksTableContent.ExpandableContent(tableContent))
            }
            'P' -> {
                val tableContent = getCoursePartWorksTable(allWorks, path.substring(1).toLong())
                _summary.postValue(summarizeWorks(tableContent))
                _tableContent.postValue(MarksTableContent.WorkContent(tableContent))
            }
            else -> TODO()
        }
    }
}