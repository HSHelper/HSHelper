package ru.hsHelper.androidApp.ui.login

import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseUser

sealed class LoginResult {
    data class Success(val success: FirebaseUser) : LoginResult()
    data class Error(@StringRes val error: Int) : LoginResult()
}
