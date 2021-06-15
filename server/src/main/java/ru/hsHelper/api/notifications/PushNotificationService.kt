package ru.hsHelper.api.notifications

import com.google.common.collect.Sets
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.hsHelper.api.entities.*
import ru.hsHelper.api.entities.Notification.NotificationType
import ru.hsHelper.api.repositories.UserCoursePartRoleRepository
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest
import ru.hsHelper.api.requests.update.WorkUpdateRequest
import ru.hsHelper.api.services.NotificationService
import ru.hsHelper.api.services.RoleService

@Service
class PushNotificationService @Autowired constructor(
    private val firebase: FirebaseMessaging,
    private val roleService: RoleService,
    private val userCoursePartRoleRepository: UserCoursePartRoleRepository,
    notificationService: NotificationService
) {
    private val workUpdate = notificationService.getNotificationByNotificationType(NotificationType.WORKUPDATE)
    private val userWorkUpdate = notificationService.getNotificationByNotificationType(NotificationType.USERWORKUPDATE)

    fun newWork(work: Work) {
        val notification = PushNotification(
            firebase,
            "New work",
            "Created new work: ${work.name}",
            mapOf(Pair("workId", work.id.toString()))
        )
        students(work.coursePart)
            .filter { user: User -> user.notifications.contains(workUpdate) }
            .forEach(notification::send)
    }

    fun modifyWork(work: Work, workUpdateRequest: WorkUpdateRequest) {
        val involvedUsers = students(work.coursePart)
            .filter { user: User -> user.notifications.contains(workUpdate) }
            .toList()
        val data = mapOf(Pair("workId", work.id.toString()))
        if (work.name != workUpdateRequest.name) {
            val notification = PushNotification(
                firebase,
                "Work renamed",
                "${work.name} -> ${workUpdateRequest.name}",
                data
            )
            involvedUsers.forEach { notification.send(it) }
        }
        if (work.description != workUpdateRequest.description) {
            val notification = PushNotification(
                firebase,
                "Description changed",
                "${work.description} -> ${workUpdateRequest.description}",
                data
            )
            involvedUsers.forEach(notification::send)
        }
        if (work.deadline != workUpdateRequest.date) {
            val notification = PushNotification(
                firebase,
                "Deadline changed",
                "${work.deadline} -> ${workUpdateRequest.date}",
                data
            )
            involvedUsers.forEach(notification::send)
        }
        if (work.weight != workUpdateRequest.weight) {
            val notification = PushNotification(
                firebase,
                "Weight changed",
                "${work.weight} -> ${workUpdateRequest.weight}",
                data
            )
            involvedUsers.forEach(notification::send)
        }
        if (work.block != workUpdateRequest.block) {
            val notification = PushNotification(
                firebase,
                "Block changed",
                "${work.block} -> ${workUpdateRequest.block}",
                data
            )
            involvedUsers.forEach(notification::send)
        }
    }

    fun updateUserWork(userWork: UserWork, userWorkUpdateRequest: UserWorkUpdateRequest) {
        if (userWork.solution != userWorkUpdateRequest.solution) {
            val notification = PushNotification(
                firebase,
                "New solution assigned",
                userWork.work.name,
                mapOf(Pair("workId", userWork.id.workId.toString()))
            )
            teachers(userWork.work.coursePart)
                .filter { user: User -> user.notifications.contains(userWorkUpdate) }
                .forEach(notification::send)
        }
        if (userWork.mark != userWorkUpdateRequest.mark) {
            val notification = PushNotification(
                firebase,
                "New mark",
                "${userWork.work.name}: ${userWorkUpdateRequest.mark}",
                mapOf(Pair("workId", userWork.id.workId.toString()))
            )
            notification.send(userWork.user)
        }
    }

    private fun teachers(coursePart: CoursePart): Sequence<User> {
        val teacherRoles = roleService.getRoleByRoleType(Role.RoleType.TEACHER)
        return usersWithRoles(coursePart, teacherRoles)
    }

    private fun students(coursePart: CoursePart): Sequence<User> {
        val studentRoles = roleService.getRoleByRoleType(Role.RoleType.STUDENT)
        return usersWithRoles(coursePart, studentRoles)
    }

    private fun usersWithRoles(coursePart: CoursePart, roles: Set<Role?>): Sequence<User> {
        return userCoursePartRoleRepository.findAllByCoursePart(coursePart).asSequence()
            .filter { !Sets.intersection(it.roles, roles).isEmpty() }
            .map { it.user }
    }
}
