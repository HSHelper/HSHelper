package ru.hsHelper.androidApp.ui.marks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestId
import ru.hsHelper.androidApp.rest.RestProvider

class MarksViewModel : ViewModel() {
    private val _summary = MutableLiveData<MarkInterval?>()
    val summary: LiveData<MarkInterval?> = _summary

    private val _tableContent = MutableLiveData<MarksTableContent>()
    val tableContent: LiveData<MarksTableContent> = _tableContent

    fun postData(path: String) = GlobalScope.launch {
        val userId = AuthProvider.currentUser!!.getRestId()
        val allWorks = RestProvider.userApi.getAllWorksUsingGET1(userId)
        val id = path.substring(1).toLong()
        when (path[0]) {
            'G' -> {
                _summary.postValue(null)
                _tableContent.postValue(ContentBuilder.groupTable(allWorks, id))
            }
            'C' -> {
                _summary.postValue(ContentBuilder.courseMark(allWorks, id))
                _tableContent.postValue(ContentBuilder.courseTable(allWorks, id))
            }
            'P' -> {
                _summary.postValue(ContentBuilder.coursePartMark(allWorks, id))
                _tableContent.postValue(ContentBuilder.coursePartTable(allWorks, id))
            }
            else -> {
                Log.e("Bad path", "MarksViewModel: Unknown path")
            }
        }
    }
}