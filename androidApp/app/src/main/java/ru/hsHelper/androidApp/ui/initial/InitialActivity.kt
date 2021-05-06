package ru.hsHelper.androidApp.ui.initial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.hsHelper.R
import ru.hsHelper.androidApp.ui.login.LoginActivity
import ru.hsHelper.androidApp.ui.navigation.NavigationActivity

class InitialActivity : AppCompatActivity() {
    companion object {
        private const val RC_LOGIN: Int = 1
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        if (auth.currentUser == null) {
            startActivityForResult(Intent(this, LoginActivity::class.java), RC_LOGIN)
        } else {
            startLoggedIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_LOGIN) {
            if (auth.currentUser == null) {
                Toast.makeText(
                    this,
                    getString(R.string.unexpected_error),
                    Toast.LENGTH_SHORT
                ).show()
                startActivityForResult(Intent(this, LoginActivity::class.java), RC_LOGIN)
            } else {
                startLoggedIn()
            }
        }
    }

    private fun startLoggedIn() {
        startActivity(Intent(this, NavigationActivity::class.java))
        finish()
    }
}
