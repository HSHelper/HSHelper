package ru.hsHelper.androidApp.ui.marks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
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

    fun postData(path: Path) = GlobalScope.launch {
        val userId = AuthProvider.currentUser!!.getRestId()
        val allWorks = RestProvider.userApi.getAllWorksUsingGET1(userId)
        when (path) {
            is Path.Group -> {
                _summary.postValue(null)
                _tableContent.postValue(ContentBuilder.groupTable(allWorks, path.id))
            }
            is Path.Course -> {
                _summary.postValue(ContentBuilder.courseMark(allWorks, path.id))
                _tableContent.postValue(ContentBuilder.courseTable(allWorks, path.id))
            }
            is Path.CoursePart -> {
                _summary.postValue(ContentBuilder.coursePartMark(allWorks, path.id))
                _tableContent.postValue(ContentBuilder.coursePartTable(allWorks, path.id))
            }
            is Path.Root -> {
                Log.e("Bad path", "MarksViewModel: Root path")
            }
        }
    }
}
