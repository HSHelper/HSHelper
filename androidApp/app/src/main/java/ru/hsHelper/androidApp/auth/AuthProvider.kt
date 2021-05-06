package ru.hsHelper.androidApp.auth

import android.util.Patterns
import com.google.firebase.auth.*

object AuthProvider {
    private val auth = FirebaseAuth.getInstance()

    val isNotLoggedIn: Boolean
        get() = auth.currentUser == null

    val currentUser: AuthUser?
        get() = auth.currentUser

    fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun authWithGoogleToken(token: String) =
        auth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))

    fun createUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun isEmailValid(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String) =
        password.length > 5

    fun isValid(email: String, password: String) =
        isEmailValid(email) && isPasswordValid(password)
}