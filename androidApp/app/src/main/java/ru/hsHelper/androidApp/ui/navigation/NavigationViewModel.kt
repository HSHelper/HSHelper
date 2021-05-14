package ru.hsHelper.androidApp.ui.navigation

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestId
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.ui.marks.MarksActivity


class NavigationViewModel : ViewModel() {
    companion object {
        private suspend fun getMainButtons(path: String): MutableList<ButtonData> =
            when {
                path.isEmpty() -> getMainButtonsGroups()
                else -> when (path[0]) {
                    'G' -> getMainButtonsCourses(path.substring(1).toLong())
                    'C' -> getMainButtonsCourse(path.substring(1).toLong())
                    'P' -> getMainButtonsCoursePart(path.substring(1).toLong())
                    else -> TODO()
                }
            }

        private suspend fun getMainButtonsGroups(): MutableList<ButtonData> {
            val userId = AuthProvider.currentUser!!.getRestId()
            val groupRoles = RestProvider.userApi.getAllGroupsUsingGET(userId)
            return groupRoles.asSequence()
                .map { groupRole -> groupRole.group }
                .map { group ->
                    ButtonData(
                        group.name,
                        NavigationActivity.launcher(group.name, "G${group.id}")
                    )
                }
                .toMutableList()
        }

        private suspend fun getMainButtonsCourses(groupId: Long): MutableList<ButtonData> {
            val allCourses = RestProvider.groupApi.getAllCoursesUsingGET(groupId)
            return allCourses.asSequence()
                .map { course ->
                    ButtonData(
                        course.name,
                        NavigationActivity.launcher(course.name, "C${course.id}")
                    )
                }
                .toMutableList()
        }

        private suspend fun getMainButtonsCourse(courseId: Long): MutableList<ButtonData> {
            val allCourseParts = RestProvider.courseApi.getAllCoursePartsUsingGET(courseId)
            return allCourseParts.asSequence()
                .map { coursePart ->
                    ButtonData(
                        coursePart.name,
                        NavigationActivity.launcher(coursePart.name, "P${coursePart.id}")
                    )
                }
                .toMutableList()
        }

        private suspend fun getMainButtonsCoursePart(coursePartId: Long): MutableList<ButtonData> {
            val coursePart = RestProvider.coursePartApi.getCoursePartUsingGET(coursePartId)
            return mutableListOf(
                ButtonData("Spreadsheet") { view ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, coursePart.gsheetLink?.toUri())
                    view.context.startActivity(browserIntent)
                }
            )
        }
    }

    private val _mainButtons = MutableLiveData<List<ButtonData>>()
    val mainButtonsState: LiveData<List<ButtonData>> = _mainButtons

    private fun retryButton(
        e: HttpException,
        path: String,
        title: String
    ): MutableList<ButtonData> =
        mutableListOf(
            ButtonData("Error ${e.code()}\n Retry") {
                this.postData(path, title)
            }
        )


    fun postData(path: String, title: String) = GlobalScope.launch {
        val mainButtons = try {
            val buttons = getMainButtons(path)
            if (path.isNotEmpty()) {
                buttons.add(ButtonData("Marks", MarksActivity.launcher(title, path)))
            }
            buttons
        } catch (e: HttpException) {
            retryButton(e, path, title)
        }
        _mainButtons.postValue(mainButtons)
    }
}
