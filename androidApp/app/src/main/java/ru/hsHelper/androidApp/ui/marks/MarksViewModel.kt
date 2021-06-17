package ru.hsHelper.androidApp.ui.marks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestId
import ru.hsHelper.androidApp.data.Path
import ru.hsHelper.androidApp.rest.RestProvider

class MarksViewModel : ViewModel() {
    private val _summary = MutableLiveData<MarkInterval?>()
    val summary: LiveData<MarkInterval?> = _summary

    private val _tableContent = MutableLiveData<MarksTableContent>()
    val tableContent: LiveData<MarksTableContent> = _tableContent

    fun postData(path: Path) = viewModelScope.launch {
        val userId = AuthProvider.currentUser!!.getRestId()
        when (path) {
            is Path.Group -> {
                val allWorks = RestProvider.userApi.getAllUserWorksByGroupUsingGET(path.id, userId)
                _summary.postValue(null)
                _tableContent.postValue(ContentBuilder.groupTable(allWorks))
            }
            is Path.Course -> {
                val allWorks = RestProvider.userApi.getAllUserWorksByCourseUsingGET(path.id, userId)
                _summary.postValue(ContentBuilder.courseMark(allWorks))
                _tableContent.postValue(ContentBuilder.courseTable(allWorks))
            }
            is Path.CoursePart -> {
                val allWorks =
                    RestProvider.userApi.getAllUserWorksByCoursePartUsingGET(path.id, userId)
                _summary.postValue(ContentBuilder.coursePartMark(allWorks))
                _tableContent.postValue(ContentBuilder.coursePartTable(allWorks))
            }
            is Path.Root -> {
                Log.e("Bad path", "MarksViewModel: Root path")
            }
        }
    }
}
