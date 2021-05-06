package ru.hsHelper.androidApp.ui.navigation

import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestId
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.rest.RestProvider

class NavigationViewModel : ViewModel() {
    companion object {
        suspend fun getMainButtons(path: String): List<ButtonData> =
            when {
                path.isEmpty() -> getMainButtonsGroups()
                else -> when (path[0]) {
                    'G' -> getMainButtonsCourses(path.substring(1).toLong())
                    'C' -> getMainButtonsCourse(path.substring(1).toLong())
                    'P' -> TODO()
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
                        navigationLauncher(group.name!!, "G${group.id!!}")
                    )
                }
                .toList()
        }

        private suspend fun getMainButtonsCourses(groupId: Long): List<ButtonData> {
            val allCourses = RestProvider.userApi.getAllCoursesUsingGET(groupId)
            return allCourses.asSequence()
                .map { courseRole -> courseRole.course!! }
                .filter { course -> course.group!!.id == groupId }
                .map { course ->
                    ButtonData(
                        course.name!!,
                        navigationLauncher(course.name!!, "C${course.id!!}")
                    )
                }
                .toList()
        }

        private suspend fun getMainButtonsCourse(courseId: Long): List<ButtonData> {
            val allCourses = RestProvider.userApi.getAllCoursePartsUsingGET(courseId)
            return allCourses.asSequence()
                .map { coursePartRole -> coursePartRole.coursePart!! }
                .filter { coursePart -> coursePart.course!!.id == courseId }
                .map { coursePart ->
                    ButtonData(
                        coursePart.name!!,
                        navigationLauncher(coursePart.name!!, "P${coursePart.id!!}")
                    )
                }
                .toList()
        }

        private fun navigationLauncher(name: String, path: String) = { view: View ->
            val intent = Intent(view.context, NavigationActivity::class.java).apply {
                putExtra(NavigationActivity.nameKey, name)
                putExtra(NavigationActivity.pathKey, path)
            }
            view.context.startActivity(intent)
        }
    }

    private val _mainButtons = MutableLiveData<List<ButtonData>>()
    val mainButtonsState: LiveData<List<ButtonData>> = _mainButtons

    fun postData(path: String) = GlobalScope.launch {
        val mainButtons = getMainButtons(path)
        _mainButtons.postValue(mainButtons)
    }
}
