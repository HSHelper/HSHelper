package ru.hsHelper.androidApp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.hsHelper.R
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.calendar.Calendar
import ru.hsHelper.androidApp.ui.initial.InitialActivity

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

        //calendarAddDefaultEvent()
        val startMillis: Long = android.icu.util.Calendar.getInstance().run {
            set(2021, 8, 14, 7, 30)
            timeInMillis
        }
        val endMillis: Long = android.icu.util.Calendar.getInstance().run {
            set(2021, 8, 14, 8, 45)
            timeInMillis
        }
        Calendar.addEvent(this, "kek", "lol", startMillis, endMillis)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            this.finish()
            true
        } else {
            false
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            setSignOut()
        }

        private fun setSignOut() {
            findPreference<Preference>(resources.getString(R.string.sign_out))!!
                .setOnPreferenceClickListener {
                    try {
                        this.activity?.finishAffinity()
                        AuthProvider.signOut()
                        startActivity(Intent(this.activity, InitialActivity::class.java))
                        true
                    } catch (e: Exception) {
                        false
                    }
                }
        }
    }
}