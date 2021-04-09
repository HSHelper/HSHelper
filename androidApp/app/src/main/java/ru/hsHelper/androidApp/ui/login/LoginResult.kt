package ru.hsHelper.androidApp.ui.login

import com.google.firebase.auth.FirebaseUser

sealed class LoginResult {
    data class Success(val success: FirebaseUser) : LoginResult()
    data class Error(val error: Int) : LoginResult()
}
