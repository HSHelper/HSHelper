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
        private suspend fun getMainButtons(path: String): List<ButtonData> =
            when {
                path.isEmpty() -> getMainButtonsGroups()
                else -> when (path[0]) {
                    'G' -> getMainButtonsCourses(path.substring(1).toLong())
                    'C' -> getMainButtonsCourse(path.substring(1).toLong())
                    'P' -> getMainButtonsCoursePart(path.substring(1).toLong())
                    else -> TODO()
                }
            }

        private suspend fun getMainButtonsGroups(): List<ButtonData> {
            val userId = AuthProvider.currentUser!!.getRestId()!!
            val groupRoles = RestProvider.userApi.getAllGroupsUsingGET(userId)
            return groupRoles.asSequence()
                .map { groupRole -> groupRole.group!! }
                .map { group ->
                    ButtonData(
                        group.name!!,
                        NavigationActivity.launcher(group.name!!, "G${group.id!!}")
                    )
                }
                .toList()
        }

        private suspend fun getMainButtonsCourses(groupId: Long): List<ButtonData> {
            val allCourses = RestProvider.groupApi.getAllCoursesUsingGET(groupId)
            return allCourses.asSequence()
                .map { course ->
                    ButtonData(
                        course.name!!,
                        NavigationActivity.launcher(course.name!!, "C${course.id!!}")
                    )
                }
                .toList()
        }

        private suspend fun getMainButtonsCourse(courseId: Long): List<ButtonData> {
            val allCourses = RestProvider.courseApi.getAllCoursePartsUsingGET(courseId)
            return allCourses.asSequence()
                .map { coursePart ->
                    ButtonData(
                        coursePart.name!!,
                        NavigationActivity.launcher(coursePart.name!!, "P${coursePart.id!!}")
                    )
                }
                .toList()
        }

        private suspend fun getMainButtonsCoursePart(coursePartId: Long): List<ButtonData> {
            val coursePart = RestProvider.coursePartApi.getCoursePartUsingGET(coursePartId)
            return listOf(
                ButtonData("Spreadsheet") { view ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, coursePart.gsheetLink?.toUri())
                    view.context.startActivity(browserIntent)
                },
                ButtonData("Works", MarksActivity.launcher(coursePart.name!!, "P${coursePartId}"))
            )
        }
    }

    private val _mainButtons = MutableLiveData<List<ButtonData>>()
    val mainButtonsState: LiveData<List<ButtonData>> = _mainButtons

    private fun retryButton(e: HttpException, path: String): List<ButtonData> =
        listOf(ButtonData("Error ${e.code()}\n Retry") {
            this.postData(path)
        })


    fun postData(path: String) = GlobalScope.launch {
        val mainButtons = try {
            getMainButtons(path)
        } catch (e: HttpException) {
            retryButton(e, path)
        }
        _mainButtons.postValue(mainButtons)
    }
}
