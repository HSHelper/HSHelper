package ru.hsHelper.api.sheets.processing.update

import ru.hsHelper.api.requests.update.UserWorkUpdateRequest
import ru.hsHelper.api.services.UserService
import ru.hsHelper.api.services.WorkService

class MarkUpdate(private val workId: Long, private val userId: Long, private val mark: Double?) {
    fun submit(userService: UserService, workService: WorkService) {
        val solution = workService.getUserWork(workId, userId).solution
        userService.updateUserWork(userId, workId, UserWorkUpdateRequest(solution, mark))
    }
}