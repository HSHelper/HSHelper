package ru.hsHelper.androidApp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import ru.hsHelper.R
import ru.hsHelper.androidApp.auth.AuthUser

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val RC_GOOGLE_SIGN_IN: Int = 1
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val loginGoogle = findViewById<Button>(R.id.login_google)

        setupViewModel(email, password, loading)
        setupLogin(email, password, login, register, loading)
        setupGoogleLogin(loginGoogle)
    }

    private fun setupViewModel(
        email: EditText,
        password: EditText,
        loading: ProgressBar
    ) {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            when (loginState) {
                is LoginFormState.Valid -> {
                }
                is LoginFormState.Error -> {
                    if (loginState.emailError != null) {
                        email.error = getString(loginState.emailError)
                    }
                    if (loginState.passwordError != null) {
                        password.error = getString(loginState.passwordError)
                    }
                }
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE

            when (loginResult) {
                is LoginResult.Success -> {
                    updateUiWithUser(loginResult.success)
                    setResult(RESULT_OK)
                    finish()
                }
                is LoginResult.Error -> {
                    showLoginFailed(loginResult.error)
                }
            }
        })
    }

    private fun setupLogin(
        email: EditText,
        password: EditText,
        login: Button,
        register: Button,
        loading: ProgressBar
    ) {
        password.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    loginViewModel.login(
                        email.text.toString(),
                        password.text.toString()
                    )
            }
            false
        }

        register.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.loginDataCheck(email.text.toString(), password.text.toString())
            loginViewModel.register(email.text.toString(), password.text.toString())
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(email.text.toString(), password.text.toString())
        }
    }

    private fun setupGoogleLogin(loginGoogle: Button) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        loginGoogle.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_GOOGLE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignIn.getSignedInAccountFromIntent(data).apply {
                addOnSuccessListener {
                    loginViewModel.authWithGoogle(it.idToken!!)
                }
                addOnFailureListener {
                    showLoginFailed(R.string.google_login_failed)
                }
            }
        }
    }

    private fun updateUiWithUser(model: AuthUser) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
