package ru.hsHelper.androidApp.ui.initial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.hsHelper.R
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.ui.login.LoginActivity
import ru.hsHelper.androidApp.ui.navigation.NavigationActivity


class InitialActivity : AppCompatActivity() {
    companion object {
        private const val RC_LOGIN: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        if (AuthProvider.isNotLoggedIn) {
            startActivityForResult(Intent(this, LoginActivity::class.java), RC_LOGIN)
        } else {
            if (intent.hasExtra("notification")) {
                startFromNotification()
            } else {
                startLoggedIn()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_LOGIN) {
            if (AuthProvider.isNotLoggedIn) {
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

    private fun startFromNotification() {
        Log.d("Notification", "Starting from notification")
        Log.d("Notification", intent.getStringExtra("notification") ?: "null")
        startLoggedIn()
    }
}
