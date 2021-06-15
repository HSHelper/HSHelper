package ru.hsHelper.androidApp.rest

import ru.hsHelper.androidApp.rest.codegen.models.User
import ru.hsHelper.androidApp.rest.codegen.models.UserUpdateRequest

fun userPartialUpdateRequest(user: User): UserUpdateRequest {
    return UserUpdateRequest(
        user.email,
        user.firstName,
        user.lastName,
        user.firebaseMessagingToken
    )
}
