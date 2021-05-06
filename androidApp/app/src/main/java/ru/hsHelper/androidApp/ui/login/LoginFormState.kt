package ru.hsHelper.androidApp.ui.login

import androidx.annotation.StringRes

sealed class LoginFormState {
    data class Error(
        @StringRes
        val emailError: Int? = null,
        @StringRes
        val passwordError: Int? = null
    ) : LoginFormState()

    object Valid : LoginFormState()
}
