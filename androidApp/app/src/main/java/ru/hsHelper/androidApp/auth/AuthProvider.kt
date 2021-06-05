package ru.hsHelper.androidApp.auth

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.UserCreateRequest

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
            .addOnSuccessListener {
                if (it.additionalUserInfo!!.isNewUser) {
                    val map = it.additionalUserInfo!!.profile!!
                    registerUser(
                        it.user!!.email!!,
                        map["given_name"] as String?,
                        map["family_name"] as String?
                    )
                }
            }

    fun createUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { registerUser(it.user!!.email!!) }

    fun isEmailValid(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String) =
        password.length > 5

    fun isValid(email: String, password: String) =
        isEmailValid(email) && isPasswordValid(password)

    private fun registerUser(email: String, firstName: String? = null, lastName: String? = null) =
        GlobalScope.launch {
            try {
                RestProvider.userApi.getUserByEmailUsingGET(email)
            } catch (e: HttpException) {
                RestProvider.userApi.createUserUsingPOST(
                    UserCreateRequest(
                        email,
                        firstName ?: "First name",
                        lastName ?: "Last name"
                    )
                )
            }
        }
}