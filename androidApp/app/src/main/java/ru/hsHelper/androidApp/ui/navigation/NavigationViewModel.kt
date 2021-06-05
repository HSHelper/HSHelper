package ru.hsHelper.androidApp.ui.navigation

import android.content.Intent
import android.util.Log
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
import ru.hsHelper.androidApp.data.Path
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.ui.marks.MarksActivity


class NavigationViewModel : ViewModel() {
    companion object {
        private suspend fun getMainButtons(path: Path): MutableList<ButtonData> =
            when (path) {
                is Path.Root -> getMainButtonsGroups()
                is Path.Group -> getMainButtonsCourses(path.id)
                is Path.Course -> getMainButtonsCourse(path.id)
                is Path.CoursePart -> getMainButtonsCoursePart(path.id)
            }

        private suspend fun getMainButtonsGroups(): MutableList<ButtonData> {
            val userId = AuthProvider.currentUser!!.getRestId()
            val groupRoles = RestProvider.userApi.getAllGroupsUsingGET(userId)
            return groupRoles.asSequence()
                .map { groupRole -> groupRole.group }
                .map { group ->
                    ButtonData(
                        group.name,
                        NavigationActivity.launcher(group.name, Path.Group(group.id))
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
                        NavigationActivity.launcher(course.name, Path.Course(course.id))
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
                        NavigationActivity.launcher(coursePart.name, Path.CoursePart(coursePart.id))
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

        private fun marksButton(activity: NavigationActivity) =
            ButtonData("Marks", MarksActivity.launcher(activity.title, activity.path))

        private fun returnButton(activity: NavigationActivity): MutableList<ButtonData> =
            mutableListOf(
                ButtonData("Unexpected error\n Return back") {
                    activity.finish()
                }
            )
    }

    private val _mainButtons = MutableLiveData<List<ButtonData>>()
    val mainButtonsState: LiveData<List<ButtonData>> = _mainButtons

    private fun retryButton(
        e: HttpException, activity: NavigationActivity
    ): MutableList<ButtonData> =
        mutableListOf(
            ButtonData("Error ${e.code()}\n Retry") {
                this.postData(activity)
            }
        )


    fun postData(activity: NavigationActivity) = GlobalScope.launch {
        val mainButtons = try {
            val buttons = getMainButtons(activity.path)
            if (activity.path !is Path.Root) {
                buttons.add(marksButton(activity))
            }
            buttons
        } catch (e: HttpException) {
            retryButton(e, activity)
        } catch (e: AssertionError) {
            Log.e("Bad path", "NavigationViewModel: ${e.message}")
            returnButton(activity)
        }
        _mainButtons.postValue(mainButtons)
    }
}
