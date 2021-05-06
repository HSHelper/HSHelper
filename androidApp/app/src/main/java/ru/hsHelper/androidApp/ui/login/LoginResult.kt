package ru.hsHelper.androidApp.ui.login

import androidx.annotation.StringRes
import ru.hsHelper.androidApp.auth.AuthUser

sealed class LoginResult {
    data class Success(val success: AuthUser) : LoginResult()
    data class Error(@StringRes val error: Int) : LoginResult()
}
