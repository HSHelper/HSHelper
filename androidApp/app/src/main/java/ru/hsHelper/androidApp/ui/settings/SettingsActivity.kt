package ru.hsHelper.androidApp.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import ru.hsHelper.R
import ru.hsHelper.androidApp.ui.login.LoginActivity
import java.lang.Exception

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val out = findPreference<Preference>("sign_out")
            out!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                return@OnPreferenceClickListener signOut()
            }
        }

        private fun signOut(): Boolean {
            return try {
                FirebaseAuth.getInstance().signOut()
                this.activity?.finishAffinity()
                startActivity(Intent(this.activity, LoginActivity::class.java))
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}