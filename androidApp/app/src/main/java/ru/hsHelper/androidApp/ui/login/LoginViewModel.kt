package ru.hsHelper.androidApp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import ru.hsHelper.R
import ru.hsHelper.androidApp.auth.AuthProvider

class LoginViewModel : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        AuthProvider.signIn(email, password).apply {
            addOnSuccessListener {
                val user = it?.user
                if (user != null) {
                    _loginResult.value = LoginResult.Success(user)
                } else {
                    _loginResult.value = LoginResult.Error(R.string.login_failed)
                }
            }
            addOnFailureListener {
                _loginResult.value = LoginResult.Error(R.string.login_failed)
            }
        }
    }

    fun register(email: String, password: String) {
        if (AuthProvider.isValid(email, password)) {
            AuthProvider.createUser(email, password).apply {
                addOnSuccessListener {
                    val user = it?.user
                    if (user != null) {
                        _loginResult.value = LoginResult.Success(user)
                    } else {
                        _loginResult.value = LoginResult.Error(R.string.register_failed)
                    }
                }
                addOnFailureListener {
                    _loginResult.value = LoginResult.Error(R.string.register_failed)
                }
            }
        } else {
            _loginResult.value = LoginResult.Error(R.string.incorrect_email_or_password)
        }
    }

    fun loginDataCheck(email: String, password: String) {
        val emailError = if (AuthProvider.isEmailValid(email)) {
            null
        } else {
            R.string.invalid_email
        }
        val passwordError = if (AuthProvider.isPasswordValid(password)) {
            null
        } else {
            R.string.invalid_password
        }
        if (emailError == null && passwordError == null) {
            _loginForm.value = LoginFormState.Valid
        } else {
            _loginForm.value = LoginFormState.Error(emailError, passwordError)
        }
    }

    fun authWithGoogle(idToken: String) {
        AuthProvider.signInWithGoogleToken(idToken).addOnSuccessListener {
            _loginResult.value = LoginResult.Success(AuthProvider.currentUser!!)
        }
    }
}
