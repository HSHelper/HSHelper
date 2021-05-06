package ru.hsHelper.androidApp.auth

import com.google.firebase.auth.FirebaseUser
import ru.hsHelper.androidApp.rest.RestProvider

typealias AuthUser = FirebaseUser

suspend fun AuthUser.getRestId() = RestProvider.userApi.getUserByEmailUsingGET(email!!).id