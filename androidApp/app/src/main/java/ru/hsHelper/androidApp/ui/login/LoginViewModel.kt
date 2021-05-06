package ru.hsHelper.androidApp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import ru.hsHelper.R

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).apply {
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
        if (isEmailValid(email) && isPasswordValid(password)) {
            auth.createUserWithEmailAndPassword(email, password).apply {
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
        val emailError = if (isEmailValid(email)) {
            null
        } else {
            R.string.invalid_email
        }
        val passwordError = if (isPasswordValid(password)) {
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
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnSuccessListener {
            _loginResult.value = LoginResult.Success(auth.currentUser!!)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
